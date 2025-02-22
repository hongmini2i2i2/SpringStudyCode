package com.example.testproject.controller;
import com.example.testproject.common.exception.AroundHubException;
import com.example.testproject.common.Constants.ExceptionClass;
import jakarta.validation.Valid;
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

    @Autowired //Autowired니까 자동으로 서비스 객체 넣어줌.
    // (인터페이스를 넣어도 인터페이스 구현체에 Service 어노테이션 있어서 그거 가져와짐.)
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
    //얘는 response status에 바디로 response를 보내기 때문에 타입이 ResponseEntity<ProductDto>
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        String productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int productPrice = productDto.getProductPrice();
        int productStock = productDto.getProductStock();

        // Validation Code Example. 이거를 @Valid로 생략가능.
        /** if (productDto.getProductId().equals("") || productDto.getProductId().isEmpty()) {
            LOGGER.error("[createProduct] failed Response :: productId is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDto);
        } */

        ProductDto response = productService.saveProduct(productId, productName, productPrice, productStock);

        LOGGER.info("[ProductController] perform {} of Around Hub API.", "getProduct");
        //logback.xml 파일 밑에 루트 설정 해둔거 가져와서 메세지 넣기.
        //쉼표뒤에 있는거 중괄호 안에 넣기 가능.
        LOGGER.info("[createProduct] Response :: productId = {}, productName = {}, productPrice = {}, productStock = {}",
                response.getProductId(), response.getProductName(), response.getProductPrice(), response.getProductStock());
        return ResponseEntity.status(HttpStatus.OK).body(response); //자기 Status 커스텀 하고 싶을때는 이렇게 전달해야함.
    }

    // http://localhost:8080/api/v1/product-api/product/{productId}
    @DeleteMapping(value = "/product/{productId}")
    public ProductDto deleteProduct(@PathVariable String productId) {
        return null;
    }

    @PostMapping(value = "/product/exception")
    public void exceptionTest() throws AroundHubException { //커스텀 Exception.
        throw new AroundHubException(ExceptionClass.PRODUCT, HttpStatus.BAD_REQUEST, "의도한 에러 발생");
    }
}

