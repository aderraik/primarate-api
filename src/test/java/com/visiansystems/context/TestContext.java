package com.visiansystems.context;

import com.visiansystems.WebAppContext;
import com.visiansystems.docs.SwaggerContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({ "classpath:unit-test.properties" })
@Import({
        WebAppContext.class,
        SwaggerContext.class
})
public class TestContext {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

//    @Bean
//    public SimpleServiceImpl simpleService() {
//        return new SimpleServiceImpl();
//    }
}

