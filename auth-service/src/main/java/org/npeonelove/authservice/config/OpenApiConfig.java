package org.npeonelove.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Auth",
//                description = "Auth",
                version = "1.0.0",
                contact = @Contact(
                        name = "Kochetov Ivan"
//                        email = "mark@struchkov.dev",
//                        url = "https://mark.struchkov.dev"
                )
        )
)
public class OpenApiConfig {
    // Конфигурация для Swagger
}
