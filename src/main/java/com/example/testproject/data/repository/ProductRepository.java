package com.example.testproject.data.repository;

import com.example.testproject.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository는 JpaRepository 이미 상속받으니까 생략가능.
public interface ProductRepository extends JpaRepository<ProductEntity, String> //엔티티, primary key 타입 등록
//Jpa 레포지토리 상속시 자동으로 spring bean으로 등록되어 다른곳에서 autowired 가능.
{

}
