package com.example.restapi.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("회원 API v1")
                .pathsToMatch("/api/v1/member/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("상품 API v1")
                .pathsToMatch("/api/v1/product/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String apiVersion) {
        return new OpenAPI().info(new Info().title("API Demo")
                .version(apiVersion)
                .description("API Demo Document"));
    }
}
