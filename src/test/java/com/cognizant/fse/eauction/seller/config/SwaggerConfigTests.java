package com.cognizant.fse.eauction.seller.config;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SwaggerConfigTests {

    @InjectMocks
    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_api() {
        Assertions.assertNotNull(swaggerConfig.api());
    }
}
