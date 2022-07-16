package me.synology.gooseauthapiserver.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
public class SwaggerConfiguration {

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
        .info(new Info().title("goose-auth-api Swagger")
            .description("goose-auth-api Swagger 문서입니다! 훈수는 환영합니다!")
            .version("v0.1.0")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
            .description("goose-auth-api GitHub")
            .url("https://github.com/GHGHGHKO/goose-auth-api-server"));
  }
}
