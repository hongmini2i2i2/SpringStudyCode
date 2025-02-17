package com.example.testproject.data.handler.impl;

import com.example.testproject.data.dao.impl.ProductDAO;
import com.example.testproject.data.entity.ProductEntity;
import com.example.testproject.data.handler.ProductDataHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductDataHandlerImpl implements ProductDataHandler {

    ProductDAO productDAO;

    @Autowired
    public ProductDataHandlerImpl(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    @Override //이미 productEntity가
    public ProductEntity saveProductEntity(String productId, String productName, int productPrice,
                                           int productStock) {
        ProductEntity productEntity = new ProductEntity(productId, productName, productPrice,
                productStock);
        return productDAO.saveProduct(productEntity);
    }

    @Override
    public ProductEntity getProductEntity(String productId) {
        return productDAO.getProduct(productId);
    }
}