package com.example.testproject.data.dao.impl;

import com.example.testproject.data.dao.ProductDAO;
import com.example.testproject.data.entity.ProductEntity;
import com.example.testproject.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //서비스(비즈니스)로직 정의. Db와 Service 계층 사이에서 비즈니스 로직 수행.
//자동으로 Bean 등록.
public class ProductDAOImpl implements ProductDAO {

    ProductRepository productRepository; //접근 제어자 생략하면 같은 클래스 뿐만 아니라 같은 패키지에서도 접근 가능.

    @Autowired //빈에 등록된 productRepository 객체 주입받음...(DI).
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity saveProduct(ProductEntity productEntity) {
        productRepository.save(productEntity); //구현 안해도 기본적으로 save getbyid이런건 jpa 제공.
        return productEntity;
    }

    @Override
    public ProductEntity getProduct(String productId) {
        ProductEntity productEntity = productRepository.getById(productId);
        return productEntity;
    }

}
