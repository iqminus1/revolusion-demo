package uz.pdp.revolusiondemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

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
                                .email("iqminus7@gmail.com")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(Type.APIKEY)
                                .name("Authorization")
                                .in(HEADER)));
    }
}
