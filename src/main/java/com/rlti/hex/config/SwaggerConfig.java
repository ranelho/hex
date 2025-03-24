package com.rlti.hex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .openapi("3.0.0")
                .info(new Info()
                        .title("Pessoa API")
                        .version("1.0.0")
                        .description("API de cadastro de pessoas"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addResponses("400", createApiResponse("Bad Request"))
                        .addResponses("404", createApiResponse("Not Found"))
                        .addResponses("500", createApiResponse("Internal Server Error")));
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse()
                .description(message)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/StandardError"))));
    }
}
