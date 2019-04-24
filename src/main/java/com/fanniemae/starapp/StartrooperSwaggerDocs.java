package com.fanniemae.starapp;

import com.fanniemae.starapp.swagger.SMSFeatureDoc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class StartrooperSwaggerDocs {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Other Features")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    @Bean
    public Docket userManagementApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("SMS Features")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(SMSFeatureDoc.class))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {


        return new ApiInfo(
                "Star Trooper Playground",
                "Some custom description of API.",
                "1.0.0",
                "Terms of service",
                new Contact("Star Troopers","", "r.ryan.rivera@gmail.com"),
                "License of API", "API license URL");

    }
}
