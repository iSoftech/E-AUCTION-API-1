package com.cognizant.fse.eauction.seller.dto;

import com.cognizant.fse.eauction.seller.model.Product;
import com.cognizant.fse.eauction.seller.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Product Bid Response Class is to handle data transfer
 *
 * @author Mohamed Yusuff
 * @since 06/12/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBidResponse implements Serializable {

    private static final long serialVersionUID = 955728933773177448L;

    private HttpStatus status;
    private Product product;
    private Seller seller;
    private List<Bid> bidsPlaced;
}
