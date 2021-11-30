package com.cognizant.fse.eauction.seller.service.impl;

import com.cognizant.fse.eauction.seller.exception.InvalidDataException;
import com.cognizant.fse.eauction.seller.exception.ResourceNotExistException;
import com.cognizant.fse.eauction.seller.exception.TechnicalException;
import com.cognizant.fse.eauction.seller.model.Seller;
import com.cognizant.fse.eauction.seller.repo.SellerRepository;
import com.cognizant.fse.eauction.seller.service.SellerService;
import com.cognizant.fse.eauction.seller.service.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Seller Service Implementation Class to manage Seller Details
 *
 * @author Mohamed Yusuff
 * @since 28/11/2021
 */
@Service
public class SellerServiceImpl implements SellerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);
    
    @Resource
    private SellerRepository sellerRepository;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public Seller getSeller(Integer sellerId) {
        Seller seller;
        try {
            Optional<Seller> sellerDoc = sellerRepository.findById(sellerId);
            if (sellerDoc.isEmpty()) {
                throw new ResourceNotExistException(String.format("The requested Seller doesn't exist [sellerId: %s]", sellerId));
            } else {
                seller = sellerDoc.get();
            }
        } catch (Exception exc) {
            LOGGER.error(String.format("Error occurred when trying to fetch Seller [sellerId: %s]", sellerId), exc);
            HttpStatus httpStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class).code();
            throw new TechnicalException(exc.getMessage(), httpStatus);
        }
        return seller;
    }

    @Override
    public Seller addSeller(Seller seller) {
        try {
            validateNewSellerAndThrowException(seller);
            seller.setId(sequenceService.getNextSequence(Seller.SEQUENCE_NAME));
            seller = sellerRepository.save(seller);
        } catch (Exception exc) {
            LOGGER.error(String.format("Error occurred when trying to Add a Seller [sellerEmail: %s]", seller.getEmail()), exc);
            HttpStatus httpStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class).code();
            throw new TechnicalException(exc.getMessage(), httpStatus);
        }
        return seller;
    }

    /**
     * Validates and Throw Exception for the new Seller entry
     *
     * @param seller refers to type {@link Seller}
     */
    private void validateNewSellerAndThrowException(Seller seller) {
        Optional<Seller> sellerDoc = sellerRepository.findByEmail(seller.getEmail());
        if (sellerDoc.isPresent()) {
            seller.setId(sellerDoc.get().getId());
        }
        if (StringUtils.isBlank(seller.getFirstName())
                || (StringUtils.length(seller.getFirstName()) < 5
                    || StringUtils.length(seller.getFirstName()) > 30)) {
            throw new InvalidDataException(String.format("The seller cannot be added as the firstName parameter is " +
                    "either empty or not as per specified length as between 5 and 30 [firstName: %s, length: %s]",
                    seller.getFirstName(), StringUtils.length(seller.getFirstName())));
        }
        if (StringUtils.isBlank(seller.getLastName())
                || (StringUtils.length(seller.getLastName()) < 5
                    || StringUtils.length(seller.getLastName()) > 25)) {
            throw new InvalidDataException(String.format("The seller cannot be added as the lastName parameter is " +
                            "either empty or not as per specified length as between 5 and 25 [lastName: %s, length: %s]",
                    seller.getLastName(), StringUtils.length(seller.getLastName())));
        }
        if (StringUtils.isBlank(seller.getPhone())
                || !StringUtils.isNumeric(seller.getPhone())
                || StringUtils.length(seller.getPhone()) < 10
                || StringUtils.length(seller.getPhone()) > 10) {
            throw new InvalidDataException(String.format("The seller cannot be added as the phone parameter is " +
                            "either empty or not numeric or not as per specified length as 10 [phone: %s, length: %s]",
                    seller.getPhone(), StringUtils.length(seller.getPhone())));
        }
        if (StringUtils.isBlank(seller.getEmail())
                || !EmailValidator.getInstance().isValid(seller.getEmail())) {
            throw new InvalidDataException(String.format("The seller cannot be added as the email parameter is " +
                            "either empty or not a valid email address [email: %s]", seller.getEmail()));
        }
    }
}
