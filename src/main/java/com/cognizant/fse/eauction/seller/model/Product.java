package com.cognizant.fse.eauction.seller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="product_info")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "product-info";

    @Id
    private Integer id;
    @Field
    private String productName;
    @Field
    private String shortDescription;
    @Field
    private String detailedDescription;
    @Field
    private String category;
    @Field
    private String startingPrice;
    @Field
    private Date bidEndDate;
    @Field
    private Integer sellerId;

}
