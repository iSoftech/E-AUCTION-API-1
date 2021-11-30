package com.cognizant.fse.eauction.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private Integer pin;
    private Integer phone;
    private String email;
}
