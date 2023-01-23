package com.example.user_ingredient_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.servers.ServerVariable;
import io.swagger.v3.oas.models.servers.ServerVariables;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Value("${open_api.paths.ingredient_path}")
    private String ingredientPath;
    @Value("${open_api.paths.user_path}")
    private String userPath;
    @Value("${open_api.paths.user_ingredient_path}")
    private String userIngredientPath;

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().servers(List.of(new Server()
                        .url("http://{host}:{port}")
                        .description("custom server")
                        .variables(new ServerVariables()
                                .addServerVariable("host",new ServerVariable()
                                        .description("host name")
                                        ._default("localhost"))
                                .addServerVariable("port",new ServerVariable()
                                        .description("port value")
                                        ._default("8080")))))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description("Provide JWT token to authenticate")
                                .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi ingredientOpenApi() {
        return GroupedOpenApi.builder()
                .addOpenApiCustomizer(openApiCustomizerIngredient())
                .group("ingredient-api")
                .pathsToMatch(ingredientPath)
                .build();
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        return GroupedOpenApi.builder()
                .addOpenApiCustomizer(openApiCustomizerUser())
                .group("user-api")
                .pathsToMatch(userPath,userIngredientPath)
                .build();
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
    }
    private OpenApiCustomizer openApiCustomizerIngredient() {
        return openApi -> openApi.info(new Info()
                .title("Ingredient API")
                .description("API for interacting with ingredients")
                .version("1.0"));
    }

    private OpenApiCustomizer openApiCustomizerUser() {
        return openApi -> openApi.info(new Info()
                .title("User API")
                .description("API for interacting with users")
                .version("1.0"));
    }
}
