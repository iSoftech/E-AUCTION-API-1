package com.cognizant.fse.eauction.seller.dto;

import com.cognizant.fse.eauction.seller.model.Product;
import com.cognizant.fse.eauction.seller.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product Request Class is to handle data transfer
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private Product product;
    private Seller seller;
}
