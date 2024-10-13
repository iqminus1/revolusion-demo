package uz.pdp.revolusiondemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Revolusion hotel")
                        .version("1.0")
                        .description("For internship")
                        .contact(new Contact()
                                .name("Abdulaziz")
                                .url("https://t.me/gplw_user")
                                .email("iqminus7@gmail.com")));
    }
}

