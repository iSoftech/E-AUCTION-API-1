package com.cognizant.fse.eauction.seller.controller;

import com.cognizant.fse.eauction.seller.dto.*;
import com.cognizant.fse.eauction.seller.model.Product;
import com.cognizant.fse.eauction.seller.model.Seller;
import com.cognizant.fse.eauction.seller.service.ProductService;
import com.cognizant.fse.eauction.seller.service.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SellerControllerTests {

    private static final Logger eLog = LoggerFactory.getLogger(SellerControllerTests.class);

    private static final String CONTEXT_PATH = "/seller";
    private static final String URI_SHOW_PRODUCTS = "/show-products";
    private static final String URI_SHOW_PRODUCT = "/show-products/%s";
    private static final String URI_SHOW_PRODUCTS_BY_SELLER = "/%s/show-products";
    private static final String URI_ADD_PRODUCT = "/add-product";
    private static final String URI_ADD_PRODUCT_BY_SELLER = "/%s/add-product";
    private static final String URI_SHOW_BIDS_BY_PRODUCT_SELLER = "/show-products/%s/show-bids";
    private static final String URI_DELETE_PRODUCT = "/delete-product/%s";

    @MockBean
    RestTemplate mockRestTemplate;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ProductService productService;
    @Autowired
    SellerService sellerService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Seller seller = null;
    private Product product = null;
    private List<Bid> bids = null;

    @BeforeEach
    void init() {
        populateTestData();
        when(mockRestTemplate.exchange(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(ResponseEntity.ok(bids));
    }

    @AfterEach
    void close() {
        productService.deleteProduct(product.getId());
        sellerService.deleteSeller(seller.getId());
    }

    @Test
    @Order(1)
    void test_showProducts() throws Exception {
        Products products = Products.builder().products(Collections.singletonList(product)).build();
        mockMvc.perform(
                get(CONTEXT_PATH + URI_SHOW_PRODUCTS)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(products), true));
    }

    @Test
    @Order(2)
    void test_showProductsBySeller() throws Exception {
        mockMvc.perform(
                get(String.format(CONTEXT_PATH + URI_SHOW_PRODUCTS_BY_SELLER, seller.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(Collections.singletonList(product)), true));
    }

    @Test
    @Order(3)
    void test_showProduct() throws Exception {
        mockMvc.perform(
                get(String.format(CONTEXT_PATH + URI_SHOW_PRODUCT, product.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(product), true));
    }

    @Test
    @Order(4)
    void test_addProduct() throws Exception {
        productService.deleteProduct(product.getId());
        sellerService.deleteSeller(seller.getId());
        ProductSellerRequest productSellerRequest = new ProductSellerRequest(product, seller);
        seller.setId(seller.getId() + 1);
        product.setId(product.getId() + 1);
        product.setSellerId(seller.getId());
        ProductResponse productResponse = new ProductResponse().builder()
                .status(HttpStatus.OK).product(product).seller(seller).build();
        mockMvc.perform(
                post(CONTEXT_PATH + URI_ADD_PRODUCT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(productSellerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(productResponse), true));
    }

    @Test
    @Order(5)
    void test_addProductBySeller() throws Exception {
        productService.deleteProduct(product.getId());
        ProductRequest productRequest = new ProductRequest(product);
        product.setId(product.getId() + 1);
        mockMvc.perform(
                post(String.format(CONTEXT_PATH + URI_ADD_PRODUCT_BY_SELLER, seller.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(product), true));
    }

    @Test
    @Order(6)
    void test_showProductBids() throws Exception {
        ProductBidResponse productBidResponse = new ProductBidResponse().builder()
                .status(HttpStatus.OK).product(product).seller(seller).bidsPlaced(bids).build();
        mockMvc.perform(
                get(String.format(CONTEXT_PATH + URI_SHOW_BIDS_BY_PRODUCT_SELLER, product.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(productBidResponse), true));
    }

    @Test
    @Order(7)
    void test_deleteProduct() {
        Product productToDelete = Product.builder()
                .productName("Sentosa Landscape")
                .shortDescription(product.getShortDescription())
                .detailedDescription(product.getDetailedDescription())
                .category(product.getCategory())
                .bidEndDate(product.getBidEndDate())
                .startingPrice(product.getStartingPrice())
                .sellerId(product.getSellerId())
                .build();
        productToDelete = productService.addProduct(productToDelete);
        try {
            mockMvc.perform(
                    delete(String.format(CONTEXT_PATH + URI_DELETE_PRODUCT, productToDelete.getId())))
                    .andExpect(status().isNoContent());
        } catch (Exception exc) {
          exc.printStackTrace();
        }
    }

    private void populateTestData() {
        try {
            seller = objectMapper.readValue(new File("src/test/resources/data/seller.json"), Seller.class);
            seller = sellerService.addSeller(seller);
            product = objectMapper.readValue(new File("src/test/resources/data/product.json"), Product.class);
            product.setSellerId(seller.getId());
            product = productService.addProduct(product);
            bids = Arrays.asList(objectMapper.readValue(new File("src/test/resources/data/bids.json"), Bid[].class));
            bids.stream().forEach(bid -> bid.setProductId(product.getId()));
        } catch (Exception exc) {
            eLog.error(exc.getMessage(), exc);
        }
    }

    private String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
