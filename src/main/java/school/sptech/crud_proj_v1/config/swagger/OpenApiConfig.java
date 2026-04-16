package school.sptech.crud_proj_v1.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto Grupo 7",
                description = "Projeto de PI e Extensão Grupo 7",
                contact = @Contact(
                        name = "Vinicius Oliveira",
                        url = "https://github.com/projeto-grupo-07/spring-boot",
                        email = "vinicius.opedroso@sptech.school"
                ),
                license = @License(name = "UNLINCENSED"),
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "Bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"
)
public class OpenApiConfig {

}
