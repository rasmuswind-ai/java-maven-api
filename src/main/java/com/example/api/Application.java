package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.api")
@OpenAPIDefinition(
    info = @Info(
        title = "Simple User API",
        version = "1.0.0",
        description = "A simple REST API for managing users",
        contact = @Contact(
            name = "API Support",
            email = "support@example.com"
        )
    )
)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
