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

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwtToken = null;

        String requestTokenHeader = request.getHeader("Authorization");
        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            LOGGER.info(">>> [FILTER] Token encontrado no Header Authorization");
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        // Imprime apenas os primeiros caracteres para confirmar que leu
                        String tokenPreview = jwtToken.length() > 10 ? jwtToken.substring(0, 10) + "..." : jwtToken;
                        LOGGER.info(">>> [FILTER] Token encontrado no Cookie 'jwt': " + tokenPreview);
                        break;
                    }
                }
            } else {
                LOGGER.info(">>> [FILTER] Nenhum cookie encontrado na requisição para: " + request.getRequestURI());
            }
        }

        if (jwtToken != null) {
            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
                LOGGER.info(">>> [FILTER] Usuário extraído do token: " + username);
            } catch (ExpiredJwtException e) {
                LOGGER.warn(">>> [FILTER] Token expirado");
            } catch (Exception e) {
                LOGGER.error(">>> [FILTER] Token inválido ou erro ao extrair usuário: " + e.getMessage());
            }
        } else {
            LOGGER.warn(">>> [FILTER] Token é NULL ao final da varredura");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

                if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    LOGGER.info(">>> [FILTER] Usuário autenticado com sucesso no contexto Spring Security: " + username);
                } else {
                    LOGGER.warn(">>> [FILTER] Validação do token falhou para usuário: " + username);
                }
            } catch (Exception e) {
                LOGGER.error(">>> [FILTER] Falha ao carregar usuário do banco de dados: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}