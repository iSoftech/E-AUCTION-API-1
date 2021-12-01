package com.cognizant.fse.eauction.seller.service;

import com.cognizant.fse.eauction.seller.model.Seller;

/**
 * Seller Service Interface to manage Seller Details
 *
 * @author Mohamed Yusuff
 * @since 29/11/2021
 */
public interface SellerService {

    /**
     * Returns requested Seller
     *
     * @param sellerId refers to attribute {@code id}
     * @return a {@link Seller} identified by its id
     */
    Seller getSeller(Integer sellerId);

    /**
     * Adds a new Seller
     *
     * @param seller refers to a new instance of {@link Seller}
     * @return a newly added seller of type {@link Seller}
     */
    Seller addSeller(Seller seller);

    /**
     * Deletes an existing Seller
     *
     * @param sellerId refers to attribute {@code id}
     */
    void deleteSeller(Integer sellerId);
}
