package com.example.restapi.api.v1.product.model;

import com.example.restapi.api.v1.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 상품 Dto
 * @author kdh
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "상품 업데이트")
public class ProductDto {
    @Schema(description = "상품명", example = "신발", maxLength = 30)
    @NotEmpty(message = "상품명을 입력해주세요.")
    @Size(max = 30, message = "상품명은 30자 이하로 입력해주세요.")
    private String productName;

    @Schema(description = "상품가격", example = "150000", maxLength = 10)
    @NotNull(message = "상품가격을 입력해주세요.")
//    @Pattern(regexp ="[0-9]+", message = "상품가격은 10자리 숫자이하로 입력해주세요.")
    @Range(min=0, max=1000000000, message = "상품가격은 10자리 숫자이하로 입력해주세요.")
    private Integer productPrice;

    public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .productPrice(this.productPrice)
                .build();
    }
}
