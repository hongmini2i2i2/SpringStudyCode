package com.example.testproject.controller;

import static org.mockito.BDDMockito.given; // BDD 스타일(Mock 객체의 특정 동작을 정의)
import static org.mockito.Mockito.verify; // Mock 객체의 특정 메서드가 호출되었는지 검증
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // GET 요청을 보냄
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // POST 요청을 보냄
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // 요청/응답 결과를 출력
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // JSON 응답 검증
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // HTTP 응답 상태 검증

import com.example.testproject.controller.ProductController;
import com.example.testproject.data.dto.ProductDto;
import com.example.testproject.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach; // JUnit에서 각 테스트 실행 전 실행되는 메서드 지정
import org.junit.jupiter.api.DisplayName; // 테스트 이름을 명확하게 설정하는 어노테이션
import org.junit.jupiter.api.Test; // JUnit의 단위 테스트 어노테이션
import org.junit.jupiter.api.extension.ExtendWith; // JUnit5에서 Mockito 확장을 사용하기 위한 어노테이션
import org.mockito.InjectMocks; // Mock 객체를 주입받아 테스트할 대상 객체 생성
import org.mockito.Mock; // 가짜(Mock) 객체를 생성
import org.mockito.junit.jupiter.MockitoExtension; // JUnit5에서 Mockito를 사용할 수 있도록 설정
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType; // 요청의 Content-Type을 설정하기 위해 사용
import org.springframework.test.web.servlet.MockMvc; // Spring MVC의 웹 요청을 테스트하기 위한 객체
import org.springframework.test.web.servlet.setup.MockMvcBuilders; // MockMvc를 수동으로 설정하기 위한 빌더 클래스

// JUnit 5 (JUnit Jupiter)에서 Mockito를 확장하여 사용하도록 설정
//@ExtendWith(MockitoExtension.class)로 해서 하는것 가능. but 따로 beforeEach해서 MockMvcBuilder세팅 필요.(컨트롤러(API)테스트때만 MockMvc가 쓰이니까 그때만.)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    // MockMvc: Spring MVC 환경에서 Controller의 동작을 테스트할 수 있는 가짜 웹 요청 객체\\
    @Autowired //WebMvcTest를 사용하면 MockMvc 자동 주입해줘서 @BeforeEach Setup()으로 아래처럼 세팅 안해줘도 됨.
    private MockMvc mockMvc;

    // @Mock: ProductService의 가짜(Mock) 객체를 생성하여 사용
    @Mock
    private ProductService productService; //ProductController에서 Autowired로 productService를 받고있기 떄문에
    // 같은 타입으로 Mock객체 생성해야함.
    //+ AutoWired는 인터페이스를 가져와도 인터페이스의 구현체Bean을 결국에는
    //가져오는거지만, Mock은 특정 구현체에 구애받지 않고 인터페이스 기반으로 가짜 객체 넣음.

    // @InjectMocks: @Mock으로 생성된 ProductService 객체를 MockController에 넣어줌.
    //ProductController에서 Autowired로 productService를 받고있기 떄문에
    //같은 타입으로 Mock객체 생성해야함.
    @InjectMocks
    private ProductController productController;

    // 각 테스트 실행 전에 실행되는 메서드
    //@BeforeEach
    //void setUp() {
        // MockMvc(가짜 클래스)를 직접 생성하여 Controller를 단독으로 테스트할 수 있도록 설정
        // mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    //}

    // 제품 정보를 가져오는 API 테스트
    @Test
    @DisplayName("Product 데이터 가져오기 테스트") // 테스트 이름을 지정하여 가독성을 높임
    void getProductTest() throws Exception {
        // given: Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
        given(productService.getProduct("12315")).willReturn(
                new ProductDto("15871", "pen", 5000, 2000));
                //willReturn: 데이터베이스에 연결하지 않아도 이러한 객체가 넘어오도록 설정하는 역할.
                //getProduct가 ProductDto 객체를 반환하기 때문에 willReturn도 당연히 마찬가지로 객체생성해서 확인.
        String productId = "12315";

        // andExpect & andDo : MockMvc를 이용해 GET 요청을 보내고 응답을 검증
        mockMvc.perform(get("/api/v1/product-api/product/" + productId)) // GET 요청 수행
                .andExpect(status().isOk()) // HTTP 상태 코드가 200인지 검증
                .andExpect(jsonPath("$.productId").exists()) // 응답 JSON에 productId가 존재하는지 확인
                .andExpect(jsonPath("$.productName").exists()) // productName 필드 검증
                .andExpect(jsonPath("$.productPrice").exists()) // productPrice 필드 검증
                .andExpect(jsonPath("$.productStock").exists()) // productStock 필드 검증
                .andDo(print()); // 요청 및 응답 내용을 콘솔에 출력

        // verify: productService.getProduct("12315") 메서드가 실제로 호출되었는지 검증
        verify(productService).getProduct("12315");
    }

    // 제품 데이터를 생성하는 API 테스트
    @Test
    @DisplayName("Product 데이터 생성 테스트") // 테스트 이름을 지정하여 가독성을 높임
    void createProductTest() throws Exception {
        // given: saveProduct 호출 시 반환할 값을 미리 설정
        given(productService.saveProduct("15871", "pen", 5000, 2000))
                .willReturn(new ProductDto("15871", "pen", 5000, 2000));

        // 테스트용 ProductDto 객체 생성
        ProductDto productDto = ProductDto.builder()
                .productId("15871")
                .productName("pen")
                .productPrice(5000)
                .productStock(2000)
                .build();

        //Gson gson = new Gson();
        //String content = gson.toJson(productDto);

        // JSON 직렬화: 자바 객체를 JSON형식의 문자열(JSON) 으로 변환 이거 아니면 위에 gson 사용해도 됨.
        String content = new ObjectMapper().writeValueAsString(productDto);

        // when & then: MockMvc를 이용해 POST 요청을 보내고 응답을 검증
        mockMvc.perform(post("/api/v1/product-api/product") // POST 요청 수행
                        .content(content) // 요청 본문에 JSON 데이터를 포함
                        .contentType(MediaType.APPLICATION_JSON)) // 요청 본문(헤더)의 Content-Type을 application/json으로 설정
                .andExpect(status().isOk()) // HTTP 상태 코드가 200인지 검증
                .andExpect(jsonPath("$.productId").exists()) // 응답 JSON에 productId가 존재하는지 확인
                .andExpect(jsonPath("$.productName").exists()) // productName 필드 검증
                .andExpect(jsonPath("$.productPrice").exists()) // productPrice 필드 검증
                .andExpect(jsonPath("$.productStock").exists()) // productStock 필드 검증
                .andDo(print()); // 요청 및 응답 내용을 콘솔에 출력

        // verify: productService.saveProduct(...) 메서드가 실행되었는지 검증
        verify(productService).saveProduct("15871", "pen", 5000, 2000);
    }
}
