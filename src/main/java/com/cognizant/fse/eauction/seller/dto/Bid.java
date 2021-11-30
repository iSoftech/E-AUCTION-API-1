package com.cognizant.fse.eauction.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    private Integer id;
    private Integer productId;
    private String bidAmount;
    private Buyer buyer;
}
