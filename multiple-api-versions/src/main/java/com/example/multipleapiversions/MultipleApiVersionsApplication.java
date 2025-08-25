package com.example.multipleapiversions;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Multiple API Versions Demo",
                version = "1.0",
                description = "Demo application showing API versioning with OpenAPI documentation.",
                contact = @Contact(name = "API Support")
        ),
        tags = {
                @Tag(name = "users-v1", description = "Operations for Users API v1 (basic fields)"),
                @Tag(name = "users-v2", description = "Operations for Users API v2 (extended fields)"),
                @Tag(name = "users-v3", description = "Operations for Users API v3 (header versioning; behaves like v1)"),
                @Tag(name = "users-v4", description = "Operations for Users API v4 (header versioning; behaves like v2)")
        }
)
@SpringBootApplication
public class MultipleApiVersionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleApiVersionsApplication.class, args);
    }

}
