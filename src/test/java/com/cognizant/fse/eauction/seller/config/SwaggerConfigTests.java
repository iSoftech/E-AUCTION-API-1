package com.cognizant.fse.eauction.seller.config;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SwaggerConfigTests {

    @InjectMocks
    private SwaggerConfig swaggerConfig;

    @Test
    void test_api() {
        Assertions.assertNotNull(swaggerConfig.api());
    }
}
