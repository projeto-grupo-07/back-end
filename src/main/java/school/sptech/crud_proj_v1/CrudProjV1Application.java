package school.sptech.crud_proj_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import school.sptech.crud_proj_v1.config.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class CrudProjV1Application {

	public static void main(String[] args) {
		SpringApplication.run(CrudProjV1Application.class, args);
	}

}
