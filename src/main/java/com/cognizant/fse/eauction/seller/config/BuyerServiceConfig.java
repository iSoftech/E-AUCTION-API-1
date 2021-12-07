package com.cognizant.fse.eauction.seller.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Buyer Service Configuration class
 *
 * @author Mohamed Yusuff
 * @since 30/11/2021
 */
@Data
@ConfigurationProperties(prefix = "app.services.buyer-service")
public class BuyerServiceConfig {
    private String scheme;
    private String host;
    private Integer port = -1;
    private String bidSearch;
}
