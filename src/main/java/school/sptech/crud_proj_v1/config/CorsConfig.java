package school.sptech.crud_proj_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Libera qualquer origem (resolve o problema do IP dinâmico)
        configuration.setAllowedOriginPatterns(List.of("*"));

        // Libera os métodos, INCLUSIVE o OPTIONS que estava sendo bloqueado
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Libera todos os cabeçalhos (Headers)
        configuration.setAllowedHeaders(List.of("*"));

        // Permite envio de credenciais/tokens
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplica essas regras para todos os endpoints da API
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}