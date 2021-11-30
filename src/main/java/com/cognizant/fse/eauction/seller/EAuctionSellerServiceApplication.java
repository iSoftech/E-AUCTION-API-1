package com.cognizant.fse.eauction.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class EAuctionSellerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionSellerServiceApplication.class, args);
	}

}


