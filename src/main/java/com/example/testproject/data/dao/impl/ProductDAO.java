package com.example.testproject.data.dao.impl;

import com.example.testproject.data.entity.ProductEntity;

public interface ProductDAO {

    ProductEntity saveProduct(ProductEntity productEntity);

    ProductEntity getProduct(String productId);

}
