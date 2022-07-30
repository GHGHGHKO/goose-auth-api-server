package me.synology.gooseauthapiserver.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
public class SwaggerConfiguration {

  @Value("${info.api.url}")
  private String apiUrl;

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("goose-auth-api-public")
        .pathsToMatch("/v1/**")
        .build();
  }

  @Bean
  public OpenAPI springGooseAuthAPI() {
    return new OpenAPI()
        .addServersItem(new Server().url(apiUrl))
        .components(new Components().addSecuritySchemes("basicScheme",
            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
        .info(new Info().title("goose-auth-api Swagger")
            .description("goose-auth-api Swagger 문서입니다! 훈수는 환영합니다!")
            .version("v0.1.0")
            .license(new License().name("Apache 2.0").url("https://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
            .description("goose-auth-api GitHub")
            .url("https://github.com/GHGHGHKO/goose-auth-api-server"));
  }
}
