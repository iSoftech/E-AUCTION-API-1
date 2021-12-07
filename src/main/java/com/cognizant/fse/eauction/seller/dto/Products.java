package com.cognizant.fse.eauction.seller.dto;

import com.cognizant.fse.eauction.seller.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Products Response Class is to handle data transfer
 *
 * @author Mohamed Yusuff
 * @since 06/12/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products implements Serializable {

    private static final long serialVersionUID = 955728933773179848L;

    private List<Product> products;
}
