package com.cognizant.fse.eauction.seller.service.impl;

import com.cognizant.fse.eauction.seller.config.BuyerServiceConfig;
import com.cognizant.fse.eauction.seller.constant.ProductCategory;
import com.cognizant.fse.eauction.seller.dto.Bid;
import com.cognizant.fse.eauction.seller.exception.*;
import com.cognizant.fse.eauction.seller.model.Product;
import com.cognizant.fse.eauction.seller.repo.ProductRepository;
import com.cognizant.fse.eauction.seller.service.ProductService;
import com.cognizant.fse.eauction.seller.service.SequenceService;
import static com.cognizant.fse.eauction.seller.util.SellerHelper.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

/**
 * Product Service Implementation Class to manage Product Details
 *
 * @author Mohamed Yusuff
 * @since 28/11/2021
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final String PARAM_PRODUCT_ID = "product-id";

    @Resource
    private ProductRepository productRepository;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private BuyerServiceConfig buyerServiceConfig;
    @Autowired
    RestTemplate restTemplate;

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
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
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
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
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
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
    }

    @Override
    public List<Bid> getProductBids(Integer productId) {
        List<Bid> bidsPlaced;
        try {
            bidsPlaced = getBidsFromBuyerService(productId);
        } catch (Exception exc) {
            ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class);
            HttpStatus httpStatus = (Objects.nonNull(responseStatus)) ? responseStatus.code() : null;
            throw new TechnicalException(exc.getMessage(), exc, httpStatus);
        }
        return bidsPlaced;
    }

    /**
     * Validates and Throw Exception for the new Product entry
     *
     * @param product refers to type {@link Product}
     */
    private void validateNewProductAndThrowException(Product product) {
        // Validates Product exist
        Optional<Product> productDoc = productRepository.findByProductName(product.getProductName());
        if (productDoc.isPresent()) {
            throw new ResourceExistException(String.format("The product cannot be added as the product already exist " +
                            "with the same name [productName: %s]", product.getProductName()));
        }
        // Validates ProductName
        if (StringUtils.isBlank(product.getProductName())
                || (StringUtils.length(product.getProductName()) < 5
                || StringUtils.length(product.getProductName()) > 30)) {
            throw new InvalidDataException(String.format("The product cannot be added as the productName parameter is " +
                            "either empty or not as per specified length as between 5 and 30 [productName: %s, length: %s]",
                    product.getProductName(), StringUtils.length(product.getProductName())));
        }
        // Validates Category
        if (StringUtils.isBlank(product.getCategory())
                || (Objects.isNull(ProductCategory.fromAlias(product.getCategory())))) {
            throw new InvalidDataException(String.format("The product cannot be added as the category parameter is " +
                            "either empty or not as per specified categories - Painting or Sculpture or Ornament [category: %s]",
                    product.getCategory()));
        }
        // Validates StartingPrice
        if (StringUtils.isBlank(product.getStartingPrice())
                || !StringUtils.isNumeric(product.getStartingPrice())) {
            throw new InvalidDataException(String.format("The product cannot be added as the startingPrice parameter is " +
                            "either empty or not numeric [startingPrice: %s]", product.getStartingPrice()));
        }
        // Validates BidEndDate
        if (Objects.isNull(product.getBidEndDate())
                || !isFutureDate(toDate(product.getBidEndDate()))) {
            throw new InvalidDataException(String.format("The product cannot be added as the bidEndDate parameter is " +
                    "either empty or not a future date [bidEndDate: %s]", product.getBidEndDate()));
        }
    }

    private void validateDeleteProductAndThrowException(Integer productId) {
        Optional<Product> productDoc = productRepository.findById(productId);
        // Validates Product exist
        if (productDoc.isEmpty()) {
            throw new ResourceNotExistException(String.format("The product cannot be deleted as the product does not exist " +
                    "[productId: %s]", productId));
        } else {
            // Validates BidEndDate
            Product product = productDoc.get();
            if (isFutureDate(toDate(product.getBidEndDate()), now())) {
                throw new InvalidOperationException(String.format("The product cannot be deleted as the bidEndDate is " +
                        "in the past from the current date [bidEndDate: %s]", product.getBidEndDate()));
            }
            // Validates Bid exist
            List<Bid> bidsPlaced = getBidsFromBuyerService(productId);
            if (CollectionUtils.isNotEmpty(bidsPlaced)) {
                throw new InvalidOperationException(String.format("The product cannot be deleted as there are active " +
                        "bids placed on this product [productId: %s, activeBidCount: %s]", productId, bidsPlaced.size()));
            }
        }
    }

    private List<Bid> getBidsFromBuyerService(Integer productId) {
        List<Bid> bids = null;
        if (Objects.nonNull(productId)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            Map<String, Integer> params = new HashMap<>();
            params.put(PARAM_PRODUCT_ID, productId);

            URI endpointUri = UriComponentsBuilder.newInstance()
                    .scheme(buyerServiceConfig.getScheme())
                    .host(buyerServiceConfig.getHost())
                    .port(buyerServiceConfig.getPort())
                    .path(buyerServiceConfig.getBidSearch())
                    .buildAndExpand(params).toUri();

            // Invokes Seller Service to retrieve Product
            ResponseEntity<Bid[]> responseEntity = restTemplate.exchange(endpointUri, HttpMethod.GET,
                    httpEntity, Bid[].class);
            if (responseEntity.getStatusCode().is2xxSuccessful()
                    && responseEntity.hasBody()
                    && ArrayUtils.isNotEmpty(responseEntity.getBody())) {
                bids = Arrays.asList(responseEntity.getBody());
            }
        }
        return bids;
    }
}
