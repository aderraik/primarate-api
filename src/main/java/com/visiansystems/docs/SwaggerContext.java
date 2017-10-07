package com.visiansystems.docs;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerContext {

    @Bean
    public Docket docket() {
        Predicate<String> paths = PathSelectors.ant("/justarate/**");

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Justarate API")
                .contact("visiansystems.com").version("0.0.1").build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo).select().paths(paths).build();

        return docket;
    }
}
