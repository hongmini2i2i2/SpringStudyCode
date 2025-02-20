package com.example.testproject.service.impl;

import static org.mockito.Mockito.verify;

import com.example.testproject.data.dao.ProductDAO;
import com.example.testproject.data.dto.ProductDto;
import com.example.testproject.data.entity.ProductEntity;
import com.example.testproject.data.handler.ProductDataHandler;
import com.example.testproject.data.handler.impl.ProductDataHandlerImpl;
import com.example.testproject.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)//로 해서 하는것 가능. but 따로 beforeEach해서 MockMvcBuilder세팅 필요.
//@SpringBootTest(classes = {ProductDataHandlerImpl.class, ProductServiceImpl.class})
//@ExtendWith(SpringExtension.class) //@SpringBootTest는 Extendwith를 포함하고 있기 때문에 한줄로 작성 가능.
//@Import({ProductDataHandlerImpl.class, ProductServiceImpl.class}) //But. ExtendWith로 하면 Import도 넣어줘야함.
public class ProductServiceImplTest {
    @Mock
    private ProductDataHandler productDataHandler;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getProductTest() {
        //given
        Mockito.when(productDataHandler.getProductEntity("123"))
                .thenReturn(new ProductEntity("123", "pen", 2000, 3000));
        ProductDto productDto = productService.getProduct("123");
        Assertions.assertEquals(productDto.getProductId(), "123");
        verify(productDataHandler).getProductEntity("123");
    }

    @Test
    public void saveProductTest() {
        //given
        Mockito.when(productDataHandler.saveProductEntity("123", "pen", 2000, 3000))
                .thenReturn(new ProductEntity("123", "pen", 2000, 3000));
        ProductDto productDto = productService.saveProduct("123", "pen", 2000, 3000);
        Assertions.assertEquals(productDto.getProductId(), "123");
        Assertions.assertEquals(productDto.getProductName(), "pen");
        Assertions.assertEquals(productDto.getProductPrice(), 2000);
        Assertions.assertEquals(productDto.getProductStock(), 3000);
    }
}
