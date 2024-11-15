package chien.demo.shopdemo.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Open api config.
 */
@Configuration
public class OpenApiConfig {

    ///**
    // * GroupedOpenApi.
    // *
    // * @param apiDocs String
    // * @return GroupedOpenApi grouped open api
    // */
    //@Bean
    //public GroupedOpenApi publicApi(@Value("${openapi.service.api-docs}") String apiDocs) {
    //    return GroupedOpenApi.builder()
    //            .group(apiDocs) // /v3/api-docs/api-service
    //            .packagesToScan("com.api.nisshin.controller")
    //            .build();
    //}

    /**
     * OpenAPI.
     *
     * @param title     api-service
     * @param version   1.0.0
     * @param serverUrl http://localhost:8080
     * @return new OpenAPI()
     */
    @Bean
    public OpenAPI openApi(
            @Value("${openApi.info.title}") String title,
            @Value("${openApi.info.version}") String version,
            @Value("${openApi.info.url}") String serverUrl) {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .servers(List.of(new Server().url(serverUrl)))
                .info(new Info()
                        .title(title)
                        .description("API documents")
                        .version(version)
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}