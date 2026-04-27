package school.sptech.crud_proj_v1.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import school.sptech.crud_proj_v1.service.AutenticacaoService;

import java.io.IOException;
import java.util.Objects;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;
    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService,
                              GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // =========================
        // BYPASS DE ENDPOINTS PÚBLICOS
        // =========================
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwtToken = null;

        // =========================
        // TOKEN VIA HEADER
        // =========================
        String authHeader = request.getHeader("Authorization");

        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            LOGGER.info(">>> TOKEN via HEADER encontrado");
        }

        // =========================
        // TOKEN VIA COOKIE
        // =========================
        if (jwtToken == null) {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        LOGGER.info(">>> TOKEN via COOKIE encontrado");
                        break;
                    }
                }
            }
        }

        // =========================
        // EXTRAÇÃO DO USER
        // =========================
        if (jwtToken != null) {
            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                LOGGER.warn("Token expirado");
            } catch (Exception e) {
                LOGGER.error("Token inválido: " + e.getMessage());
            }
        }

        // =========================
        // AUTENTICAÇÃO NO CONTEXTO
        // =========================
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

                if (jwtTokenManager.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    LOGGER.info("Usuário autenticado: " + username);
                }

            } catch (Exception e) {
                LOGGER.error("Erro ao autenticar usuário: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return path.equals("/health")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs")
                || path.equals("/funcionarios/login");
    }
}