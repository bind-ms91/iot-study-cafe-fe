package bind.iotstudycafe.commons.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Bind Iot Study Cafe API Docs",
                description = "바인드 Iot Study Cafe API 문서",
                version = "v1"
        )
//        ,servers = {
//                @Server(url = "/api/swagger", description = "Default swagger URL")
//        }
)

@Configuration
public class SwaggerConfig {

//        @Bean
//        public OpenAPI customOpenAPI() {
//
//                return new OpenAPI()
//                        .info(new Info()
//                                .title("API Documentation")
//                                .version("1.0"))
//                        .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
//                        .components(new io.swagger.v3.oas.models.Components()
//                                .addSecuritySchemes("basicAuth",
//                                        new SecurityScheme()
//                                                .type(SecurityScheme.Type.HTTP)
//                                                .scheme("basic")));
//        }


        @Bean
        public GroupedOpenApi exampleApi() {

                String[] pathsToMatch = {"/example/**"};

                return GroupedOpenApi.builder()
                        .group("예제그룹")
                        .pathsToMatch("/example/**")            // group에 포함시킬 paths
                        .build();
        }

        @Bean
        public GroupedOpenApi publicApi() {

                String[] pathsToMatch = {"/**"};
                String[] pathsToExclude = {"/example/**"};

                return GroupedOpenApi.builder()
                        .group("public")
                        .pathsToMatch(pathsToMatch)
                        .pathsToExclude(pathsToExclude)
                        .build();
        }


}
