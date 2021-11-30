package com.cognizant.fse.eauction.seller.common;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@RestController
@RequestMapping
public @interface RestApiController {

    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String[] value();
}
