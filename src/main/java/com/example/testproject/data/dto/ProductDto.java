package com.example.testproject.data.dto;
import com.example.testproject.data.entity.ProductEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {
    @NotNull
    //@Size(min = 8, max = 8)
    private String productId;

    @NotNull
    private String productName;

    @NotNull
    @Min(value = 500)
    @Max(value = 3000000)
    private Integer productPrice;

    @NotNull
    @Min(value = 0) //재고는 마이너스 안돼
    @Max(value = 9999)
    private Integer productStock;

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productStock(productStock)
                .build();
    }

}
