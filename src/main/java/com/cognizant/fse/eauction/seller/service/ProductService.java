package com.cognizant.fse.eauction.seller.service;

import com.cognizant.fse.eauction.seller.model.Product;

import java.util.List;

/**
 * Product Service Interface to manage Product Details
 *
 * @author Mohamed Yusuff
 * @since 28/11/2021
 */
public interface ProductService {

    /**
     * Returns all Products
     *
     * @return a list of {@link Product}
     */
    List<Product> getAllProducts();

    /**
     * Returns all Products for the given Seller
     *
     * @param sellerId refers to attribute {@code sellerId}
     * @return a list of {@link Product}
     */
    List<Product> getAllProducts(Integer sellerId);

    /**
     * Returns requested Product
     *
     * @param productId refers to attribute {@code id}
     * @return a {@link Product} identified by its id
     */
    Product getProduct(Integer productId);

    /**
     * Adds a new Product
     *
     * @param product refers to a new instance of {@link Product}
     * @return a newly added product of type {@link Product}
     */
    Product addProduct(Product product);

    /**
     * Deletes an Existing Product
     *
     * @param productId refers to attribute {@code id}
     */
    void deleteProduct(Integer productId);
}
