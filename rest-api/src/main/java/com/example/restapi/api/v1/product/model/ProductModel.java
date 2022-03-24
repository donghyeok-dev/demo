package com.example.restapi.api.v1.product.model;

import com.example.restapi.global.model.AbstractRepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 상품 Model
 * @author kdh
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
@Schema(description = "상품")
public class ProductModel extends AbstractRepresentationModel<ProductModel> {
    @Schema(description = "상품번호", example = "2")
    private Long productId;

    @Schema(description = "상품명", example = "신발", maxLength = 30)
    private String productName;

    @Schema(description = "상품가격", example = "150000", maxLength = 10)
    @NotEmpty(message = "상품가격을 입력해주세요.")
    @Pattern(regexp ="[0-9]{1,10}$", message = "상품가격은 10자리 숫자이하로 입력해주세요.")
    private Integer productPrice;
}
