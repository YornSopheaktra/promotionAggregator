package com.promotion.aggregate.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.UnknownHostException;

@Configuration
@EnableSwagger2
public class SwaggerConfigure {

    @Bean
    public Docket api() throws UnknownHostException {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.promotion.aggregate.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Promotion Aggregation",
                "Promotion Aggregation description about this API.",
                "v0.1",
                "Terms of service",
                new Contact("Sopheaktra", "https://swagger.io/swagger-ui", "sopheaktra.yor@ascendcorp.com"),
                "License of API",
                "API license URL");
        return apiInfo;
    }

}
