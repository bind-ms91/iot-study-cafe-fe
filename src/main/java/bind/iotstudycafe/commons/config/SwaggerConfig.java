package bind.iotstudycafe.commons.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Bind Iot Study Cafe API Docs",
                description = "바인드 Iot Study Cafe API 문서",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

//        @Bean
//        public OpenAPI customOpenAPI() {
//                return new OpenAPI()
//                        .info(new Info().title("Example API")
//                                .description("Example API documentation")
//                                .version("v1.0"));
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
