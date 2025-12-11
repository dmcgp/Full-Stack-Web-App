package com.example.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI api() {
    final String schemeName = "bearerAuth";
    return new OpenAPI()
        .info(new Info()
            .title("Task Manager API")
            .version("0.1.0")
            .description("REST API for projects, tasks, and comments"))
        .components(new Components()
            .addSecuritySchemes(schemeName, new SecurityScheme()
                .name(schemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList(schemeName));
  }
}
package com.example.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
            .title("Task Manager API")
            .version("0.1.0")
            .description("REST API for projects, tasks, and comments"));
    }
}
