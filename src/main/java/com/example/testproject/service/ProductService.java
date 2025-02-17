package com.example.testproject.service;

import com.example.testproject.data.dto.ProductDto;

public interface ProductService { //루즈 커플링으로 의존성을 줄이기 위해 인터페이스, impl 따로 세팅.

    ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);

    ProductDto getProduct(String productId);

}
