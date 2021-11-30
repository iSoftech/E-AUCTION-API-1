package com.cognizant.fse.eauction.seller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EAuctionSellerServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test_main() {
        EAuctionSellerServiceApplication.main(new String[]{"spring.profiles.active=test"});
    }
}
