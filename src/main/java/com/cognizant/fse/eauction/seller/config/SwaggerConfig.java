package com.cognizant.fse.eauction.seller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration
 *
 * @author Mohamed Yusuff
 * @since 20/11/2021
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.cognizant.fse.eauction.seller.controller"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("E-Auction Seller Service API")
                .description("E-Auction Seller REST Service API")
                .version("1.0")
                .termsOfServiceUrl("Terms of Service")
                .contact(new Contact("Mohamed Yusuff", "https://github.com/iSoftech/e-auction-seller-service/", "me@contact.com"))
                .license("License of API")
                .licenseUrl("API License URL")
                .build();
    }

}
