package com.cognizant.fse.eauction.seller.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class HealthControllerTests {

    @InjectMocks
    private HealthController healthController;

    @Test
    void test_nodeStatus() {
        final ResponseEntity<HttpStatus> status = healthController.nodeStatus();
        Assertions.assertEquals(HttpStatus.OK, status.getBody());
    }
}
