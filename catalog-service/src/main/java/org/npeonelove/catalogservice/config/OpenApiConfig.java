package org.npeonelove.catalogservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Online Store",
                version = "0.0.1",
                contact = @Contact(
                        name = "Kochetov Ivan",
                        url = "https://t.me/NPEonelove"
                )
        )
)
public class OpenApiConfig {

}