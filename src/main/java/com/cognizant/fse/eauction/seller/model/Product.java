package com.cognizant.fse.eauction.seller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="product_info")
public class Product implements Serializable {

    private static final long serialVersionUID = 955728933773177564L;

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
    private String bidEndDate;
    @Field
    private Integer sellerId;

}
