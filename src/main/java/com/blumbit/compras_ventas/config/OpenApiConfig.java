package com.blumbit.compras_ventas.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Your API",  // Customize as needed
        version = "1.0",
        description = "API Documentation with JWT Authentication"
    ),
    security = @SecurityRequirement(name = "bearerAuth")  // Applies JWT globally
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT token for authentication. Enter '<token>'",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER  // Token in Authorization header
)
public class OpenApiConfig {
    // Empty class - annotations handle the configuration
}
