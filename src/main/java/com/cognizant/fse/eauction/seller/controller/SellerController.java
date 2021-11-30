package com.cognizant.fse.eauction.seller.controller;

import com.cognizant.fse.eauction.seller.common.RestApiController;
import com.cognizant.fse.eauction.seller.dto.ProductRequest;
import com.cognizant.fse.eauction.seller.dto.ProductResponse;
import com.cognizant.fse.eauction.seller.model.Product;
import com.cognizant.fse.eauction.seller.model.Seller;
import com.cognizant.fse.eauction.seller.service.ProductService;
import com.cognizant.fse.eauction.seller.service.SellerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Seller Controller to perform seller actions
 *
 * @author Mohamed Yusuff
 * @since 28/11/2021
 */
@Api(tags = "E-Auction Seller REST Controller")
@RestApiController("seller")
@RequiredArgsConstructor
public class SellerController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SellerService sellerService;

    /**
     * Returns all Products
     *
     * @return a {@link List} of type {@link Product}
     */
    @ApiOperation(value = "Show all Products", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping("show-products")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Returns all Products for the given Seller Id
     *
     * @param sellerId refers to attribute {@code sellerId}
     * @return a {@link List} of type {@link Product}
     */
    @ApiOperation(value = "Show all Products for the given SellerId", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping("{seller-id}/show-products")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProductsBySeller(@PathVariable("seller-id") Integer sellerId) {
        List<Product> products = productService.getAllProducts(sellerId);
        return ResponseEntity.ok(products);
    }

    /**
     * Returns the newly added Product
     *
     * @param productRequest of type {@link ProductRequest}
     * @return the newly added product of type {@link Product}
     */
    @ApiOperation(value = "Adds a new Seller and Product", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping("add-product")
    @ResponseBody
    public ResponseEntity<ProductResponse> addProduct(@RequestBody final ProductRequest productRequest) {
        Seller seller = sellerService.addSeller(productRequest.getSeller());
        productRequest.getProduct().setSellerId(seller.getId());
        Product product = productService.addProduct(productRequest.getProduct());
        ProductResponse productResponse = ProductResponse.builder()
                .status(HttpStatus.OK)
                .product(product)
                .seller(seller)
                .build();
        return ResponseEntity.ok(productResponse);
    }

    /**
     * Returns the newly added Product
     *
     * @param product of type {@link Product}
     * @return the newly added product of type {@link Product}
     */
    @ApiOperation(value = "Adds a new Product for an existing Seller", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping("/{seller-id}/add-product")
    @ResponseBody
    public ResponseEntity<Product> addProductBySeller(@PathVariable("seller-id") Integer sellerId,
                                                      @RequestBody final Product product) {
        product.setSellerId(sellerId);
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    /**
     * Returns the Product Details with Bids Placed
     *
     * @param productId refers to attribute {@code id} of type {@link Product}
     * @return the product response of type {@link ProductResponse}
     */
    @ApiOperation(value = "Shows the Product details with the list of bids placed", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping("show-bids/{product-id}")
    @ResponseBody
    public ResponseEntity<ProductResponse> showProductBids(@PathVariable("product-id") Integer productId) {
        Product product = productService.getProduct(productId);
        Seller seller = sellerService.getSeller(product.getSellerId());
        ProductResponse productResponse = ProductResponse.builder()
                .status(HttpStatus.OK)
                .product(product)
                .seller(seller)
                .build();
        return ResponseEntity.ok(productResponse);
    }

    /**
     * Deletes the given Product
     *
     * @param productId refers to attribute {@code id} of type {@link Product}
     */
    @ApiOperation(value = "Deletes the given Product", response = Product.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Product.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping("/delete/{product-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity.HeadersBuilder<?> deleteProduct(@PathVariable("product-id") Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent();
    }
}
