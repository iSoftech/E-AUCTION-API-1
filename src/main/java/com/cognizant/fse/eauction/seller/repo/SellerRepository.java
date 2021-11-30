package com.cognizant.fse.eauction.seller.repo;

import com.cognizant.fse.eauction.seller.model.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends MongoRepository<Seller, Integer> {

    Optional<Seller> findByEmail(String email);
}
