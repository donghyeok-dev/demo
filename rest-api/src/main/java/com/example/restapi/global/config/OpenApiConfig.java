package com.example.restapi.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String apiVersion) {
        return new OpenAPI().info(new Info().title("API Demo")
                .version(apiVersion)
                .description("API Demo Document"));
    }
}
