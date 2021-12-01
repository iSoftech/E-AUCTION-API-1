package com.cognizant.fse.eauction.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer implements Serializable {

    private static final long serialVersionUID = 955728933773177418L;

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private Integer pin;
    private String phone;
    private String email;
}
