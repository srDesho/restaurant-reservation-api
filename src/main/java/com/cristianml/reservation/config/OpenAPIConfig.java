package com.cristianml.reservation.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;


// http://localhost:8080/api/v1/swagger-ui/index.html
@Configuration
public class OpenAPIConfig {

    @Value("${cristian.openapi.dev-url}")
    private String devUrl;

    @Value("${cristian.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        // Define the development server
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development environment server URL");

        // Define the production server
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Production environment server URL");

        // API contact information
        Contact contact = new Contact();
        contact.setEmail("cristian@gmail.com");
        contact.setName("Cristian Desho");
        contact.setUrl("https://cristianml.com");

        // License information
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        // General API information
        Info info = new Info()
                .title("Restaurant Reservation API")
                .version("1.0")
                .contact(contact)
                .description("This API provides endpoints for managing restaurant reservations.")
                .termsOfService("https://www.cristianml.com/terms")
                .license(mitLicense);

        // Configure JWT-based security
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication");

        // Security components setup
        Components components = new Components()
                .addSecuritySchemes("bearerAuth", securityScheme);

        // Security requirement for API operations
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        // Build the OpenAPI configuration
        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}