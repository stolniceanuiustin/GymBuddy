package com.gymbuddy.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymBuddyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GymBuddy API")
                        .description("API for tracking gym workouts and progress")
                        .version("1.0.0"));
    }
}
