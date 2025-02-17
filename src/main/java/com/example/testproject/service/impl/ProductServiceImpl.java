package com.example.testproject.service.impl;
import com.example.testproject.data.dto.ProductDto;
import com.example.testproject.data.entity.ProductEntity;
import com.example.testproject.data.handler.ProductDataHandler;
import com.example.testproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductServiceImpl implements ProductService {
    ProductDataHandler productDataHandler;
    @Autowired
    public ProductServiceImpl(ProductDataHandler productDataHandler){
        this.productDataHandler = productDataHandler;
    }
    @Override
    public ProductDto saveProduct(String productId, String productName, int productPrice, int productStock) {
        ProductEntity productEntity = productDataHandler.saveProductEntity(productId, productName, productPrice, productStock);
        ProductDto productDto = new ProductDto(productEntity.getProductId(),
                productEntity.getProductName(), productEntity.getProductPrice(), productEntity.getProductStock());
        return productDto; //DTO 리턴은 엔티티 저장 제대로 됐나 확인용???
    }
    @Override
    public ProductDto getProduct(String productId) {
        ProductEntity productEntity = productDataHandler.getProductEntity(productId);
        ProductDto productDto = new ProductDto(productEntity.getProductId(),
                productEntity.getProductName(), productEntity.getProductPrice(), productEntity.getProductStock());
        return productDto;
    }
}