package com.github.vadim01er.testtaskntiteam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Swagger config.
 */
@Configuration
public class MySwaggerConfig {
    /**
     * Custom open api.
     *
     * @return the {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Lord and Planet API (NTI Team)")
                        .version("v1")
                        .description("Test task for company NTI Team \n Use database HyperSQL")
                        .contact(new Contact()
                                .name("Vadim Ershov")
                                .email("vadim.ershov.01@mail.ru")
                                .url("https://github.com/vadim01er"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
