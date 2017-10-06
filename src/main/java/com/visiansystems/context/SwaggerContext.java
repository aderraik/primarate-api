package com.visiansystems.context;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The ApiDocsConfiguration class provides configuration beans for the Swagger API documentation
 * generator.
 */
@EnableSwagger2
@Configuration
public class SwaggerContext {

    /**
     * Create a Docket class to be used by Springfox's Swagger API Documentation framework.
     * See http://springfox.github.io/springfox/ for more information.
     *
     * @return A Docket instance.
     */
    @Bean
    public Docket docket() {
        Predicate<String> paths = PathSelectors.ant("/tutorial/**");

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Visian Spring Tutorial")
                .contact("visiansystems.com").version("0.0.1").build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo).select().paths(paths).build();

        return docket;
    }
}
