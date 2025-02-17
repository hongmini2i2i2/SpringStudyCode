package com.example.testproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.testproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.testproject.data.dto.ProductDto;

@RestController
@RequestMapping("/api/v1/product-api")
//@Tag(name="")로 product-controller로 자동생성 안시키고 swagger 소제목 변경 가능.
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);//resource에 설정된 로거 겟
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // http://localhost:8080/api/v1/product-api/product/{productId}
    @GetMapping(value = "/product/{productId}")
    public ProductDto getProduct(@PathVariable String productId) {
        long startTime =System.currentTimeMillis();
        LOGGER.info("[ProductController] perform {} of Around Hub API.", "getProduct");
        //logback.xml 파일 밑에 루트 설정 해둔거 가져와서 메세지 넣기.
        //쉼표뒤에 있는거 중괄호 안에 넣기 가능.
        ProductDto productDto =productService.getProduct(productId);
        LOGGER.info("[ProductController] Response :: productId = {}, productName = {}, productPrice = {}, productStock = {}, Response Time = {}ms", productDto.getProductId(),
                productDto.getProductName(), productDto.getProductPrice(), productDto.getProductStock(), (System.currentTimeMillis()- startTime));
        return productDto;
    }

    // http://localhost:8080/api/v1/product-api/product
    @PostMapping(value = "/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        String productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int productPrice = productDto.getProductPrice();
        int productStock = productDto.getProductStock();

        ProductDto response = productService.saveProduct(productId, productName, productPrice, productStock);

        LOGGER.info("[ProductController] perform {} of Around Hub API.", "getProduct");
        //logback.xml 파일 밑에 루트 설정 해둔거 가져와서 메세지 넣기.
        //쉼표뒤에 있는거 중괄호 안에 넣기 가능.
        LOGGER.info("[createProduct] Response :: productId = {}, productName = {}, productPrice = {}, productStock = {}",
                response.getProductId(), response.getProductName(), response.getProductPrice(), response.getProductStock());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // http://localhost:8080/api/v1/product-api/product/{productId}
    @DeleteMapping(value = "/product/{productId}")
    public ProductDto deleteProduct(@PathVariable String productId) {
        return null;
    }
}

