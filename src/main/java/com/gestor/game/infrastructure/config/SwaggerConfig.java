package com.gestor.game.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestor de Builds y Personajes")
                        .version("1.0")
                        .description("Documentación interactiva del backend para el juego. " +
                                "Aquí puedes probar la creación de usuarios, ítems, builds y personajes."));
    }
}
// http://localhost:8080/swagger-ui/index.html#/
// esa me funciono