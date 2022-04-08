package uk.gov.dwp.users.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI getApiDefinition() {
        Info info = new Info()
                .title("DWP Digital Users Service")
                .version("v1")
                .description("Java API serving User information as part of the recruitment test for DWP.");

        return new OpenAPI().info(info);
    }
}
