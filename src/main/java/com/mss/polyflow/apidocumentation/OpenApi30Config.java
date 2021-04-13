package com.mss.polyflow.apidocumentation;//package com.example.demo.apidocumentation;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.info.Info;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@OpenAPIDefinition(info = @Info(title = "User Management", version = "v1"))
///*@SecurityScheme(
//    name = "basicAuth",
//    type = SecuritySchemeType.HTTP,
//    scheme = "basic"
//)*/
//@SecurityScheme(
//    name = "bearerAuth",
//    type = SecuritySchemeType.HTTP,
//    bearerFormat = "JWT",
//    scheme = "bearer"
//)
//public class OpenApi30Config {
//
//}
//

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi30Config {

    private final String apiTitle;
    private final String apiVersion;
    private final String apiDescription;

    public OpenApi30Config(
        @Value("${application-title}") String apiTitle,
        @Value("${application-version}") String apiVersion,
        @Value("${application-description}") String apiDescription) {
        this.apiTitle = apiTitle;
        this.apiVersion = apiVersion;
        this.apiDescription = apiDescription;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String formattedApiTitle = String.format("%s API", StringUtils.capitalize(apiTitle));
        return new OpenAPI()
                   .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                   .components(
                       new Components()
                           .addSecuritySchemes(securitySchemeName,
                               new SecurityScheme()
                                   .name(securitySchemeName)
                                   .type(SecurityScheme.Type.HTTP)
                                   .scheme("bearer")
                                   .bearerFormat("JWT")
                           )
                   )
                   .info(new Info()
                             .title(formattedApiTitle)
                             .version(apiVersion)
                             .description(apiDescription)
                             .termsOfService("http://swagger.io/terms/")
                             .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
