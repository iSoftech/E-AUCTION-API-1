package com.cognizant.fse.eauction.seller.service.impl;

import com.cognizant.fse.eauction.seller.contant.ProductCategory;
import com.cognizant.fse.eauction.seller.exception.*;
import com.cognizant.fse.eauction.seller.model.Product;
import com.cognizant.fse.eauction.seller.repo.ProductRepository;
import com.cognizant.fse.eauction.seller.service.ProductService;
import com.cognizant.fse.eauction.seller.service.SequenceService;
import com.cognizant.fse.eauction.seller.util.ProductHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Product Service Implementation Class to manage Product Details
 *
 * @author Mohamed Yusuff
 * @since 28/11/2021
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private ProductRepository productRepository;
    @Autowired
    private SequenceService sequenceService;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProducts(Integer sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    @Override
    public Product getProduct(Integer productId) {
        Product product;
        try {
            Optional<Product> productDoc = productRepository.findById(productId);
            if (productDoc.isEmpty()) {
                throw new ResourceNotExistException(String.format("The requested Product doesn't exist [productId: %s]", productId));
            } else {
                product = productDoc.get();
            }
        } catch (Exception exc) {
            LOGGER.error(String.format("Error occurred when trying to fetch Product [productId: %s]", productId), exc);
            HttpStatus httpStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class).code();
            throw new TechnicalException(exc.getMessage(), httpStatus);
        }
        return product;
    }

    @Override
    public Product addProduct(Product product) {
        try {
            validateNewProductAndThrowException(product);
            product.setId(sequenceService.getNextSequence(Product.SEQUENCE_NAME));
            product = productRepository.save(product);
        } catch (Exception exc) {
            LOGGER.error(String.format("Error occurred when trying to Add a Product [productName: %s]", product.getProductName()), exc);
            HttpStatus httpStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class).code();
            throw new TechnicalException(exc.getMessage(), httpStatus);
        }
        return product;
    }

    @Override
    public void deleteProduct(Integer productId) {
        try {
            validateDeleteProductAndThrowException(productId);
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                productRepository.delete(product.get());
            }
        } catch (Exception exc) {
            LOGGER.error(String.format("Error occurred when trying to Delete a Product [productId: %s]", productId), exc);
            HttpStatus httpStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class).code();
            throw new TechnicalException(exc.getMessage(), httpStatus);
        }
    }

    /**
     * Validates and Throw Exception for the new Product entry
     *
     * @param product refers to type {@link Product}
     */
    private void validateNewProductAndThrowException(Product product) {
        Optional<Product> productDoc = productRepository.findByProductName(product.getProductName());
        if (productDoc.isPresent()) {
            throw new ResourceExistException(String.format("The product cannot be added as the product already exist " +
                            "with the same name [productName: %s]", product.getProductName()));
        }
        if (StringUtils.isBlank(product.getProductName())
                || (StringUtils.length(product.getProductName()) < 5
                || StringUtils.length(product.getProductName()) > 30)) {
            throw new InvalidDataException(String.format("The product cannot be added as the productName parameter is " +
                            "either empty or not as per specified length as between 5 and 30 [productName: %s, length: %s]",
                    product.getProductName(), StringUtils.length(product.getProductName())));
        }
        if (StringUtils.isBlank(product.getCategory())
                || (Objects.isNull(ProductCategory.fromAlias(product.getCategory())))) {
            throw new InvalidDataException(String.format("The product cannot be added as the category parameter is " +
                            "either empty or not as per specified categories - Painting or Sculpture or Ornament [category: %s]",
                    product.getCategory()));
        }
        if (StringUtils.isBlank(product.getStartingPrice())
                || !StringUtils.isNumeric(product.getStartingPrice())) {
            throw new InvalidDataException(String.format("The product cannot be added as the startingPrice parameter is " +
                            "either empty or not numeric [startingPrice: %s]", product.getStartingPrice()));
        }
        if (Objects.isNull(product.getBidEndDate())
                || !ProductHelper.isFutureDate(product.getBidEndDate())) {
            throw new InvalidDataException(String.format("The product cannot be added as the bidEndDate parameter is " +
                    "either empty or not a future date [bidEndDate: %s]", product.getBidEndDate()));
        }
    }

    private void validateDeleteProductAndThrowException(Integer productId) {
        Optional<Product> productDoc = productRepository.findById(productId);
        if (productDoc.isEmpty()) {
            throw new ResourceNotExistException(String.format("The product cannot be deleted as the product does not exist " +
                    "[productId: %s]", productId));
        } else {
            Product product = productDoc.get();
            if (ProductHelper.isFutureDate(product.getBidEndDate(), Calendar.getInstance().getTime())) {
                throw new InvalidOperationException(String.format("The product cannot be deleted as the bidEndDate is " +
                        "in the past from the current date [bidEndDate: %s]", product.getBidEndDate()));
            }
            // TODO: To fetch the list of Bids available on this product and throw error if there's at least once active bid exist
        }
    }

}
