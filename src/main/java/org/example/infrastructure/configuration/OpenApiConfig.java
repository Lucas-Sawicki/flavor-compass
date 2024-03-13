package org.example.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Łukasz",
                        email = "lukasz.sawicki.gwo@gmail.com"
                ),
                description = "OpenApi Documentation for Flavour Compass - application for finding food with delivery.",
                title = "OpenApi specification - Flavour Compass",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/flavour-compass/"
                )
        }
//        security =
)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
