package com.cognizant.fse.eauction.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid implements Serializable {

    private static final long serialVersionUID = 955728933773177258L;

    private Integer id;
    private Integer productId;
    private String bidAmount;
    private Buyer buyer;
}
