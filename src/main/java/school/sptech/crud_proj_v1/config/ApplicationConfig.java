package school.sptech.crud_proj_v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public GerenciadorTokenJwt gerenciadorTokenJwt(JwtProperties jwtProperties) {
        return new GerenciadorTokenJwt(jwtProperties);
    }

}