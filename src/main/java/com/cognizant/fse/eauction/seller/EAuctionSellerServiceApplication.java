package com.cognizant.fse.eauction.seller;

import com.cognizant.fse.eauction.seller.config.BuyerServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@EnableConfigurationProperties({
		BuyerServiceConfig.class
})
public class EAuctionSellerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionSellerServiceApplication.class, args);
	}

}


