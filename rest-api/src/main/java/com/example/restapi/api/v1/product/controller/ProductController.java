package com.example.restapi.api.v1.product.controller;

import com.example.restapi.api.v1.member.model.MemberModel;
import com.example.restapi.api.v1.product.model.ProductDto;
import com.example.restapi.api.v1.product.model.ProductModel;
import com.example.restapi.api.v1.product.service.ProductService;
import com.example.restapi.global.error.exception.InvalidParameterException;
import com.example.restapi.global.response.ErrorResponse;
import com.example.restapi.global.response.LinkFormat;
import com.example.restapi.global.util.CommonApiCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import static com.example.restapi.global.util.CommonApiCode.*;

import javax.validation.Valid;

/**
 * 상품 Restful API Controller
 * @author kdh
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
@ApiResponse(responseCode = CommonApiCode.ResponseCode.ERROR_400, description = "올바르지 않는 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = CommonApiCode.ResponseCode.ERROR_500, description = "서버 내 오류 발생", content = @Content(schema = @Schema(hidden = true)))
@ApiResponse(responseCode = CommonApiCode.ResponseCode.ERROR_503, description = "네트워크 문제 또는 서버 이용 불가", content = @Content(schema = @Schema(hidden = true)))
@RequestMapping(value = "/api/v1/product", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "모든 상품 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200,
                    description = "조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductModel.class))),
                    links = {
                            @Link(name = ResponseLinkName.SELF, description = "현재", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.CREATE, description = "등록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.POST)
                            })
                    })
    })
    @GetMapping()
    public ResponseEntity<CollectionModel<ProductModel>> getAllProduct() {
        return ResponseEntity.ok()
                .body(this.productService.findAllProduct());
    }

    @Operation(summary = "상품 조회", description = "상품 id로 상품 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200, description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ProductModel.class)),
                    links =  {
                            @Link(name = ResponseLinkName.SELF, description = "현재", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.UPDATE, description = "수정", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.PUT)
                            }),
                            @Link(name = ResponseLinkName.DELETE, description = "삭제", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.DELETE)
                            }),
                            @Link(name = ResponseLinkName.LIST, description = "목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
            @ApiResponse(responseCode = ResponseCode.NO_CONTENT_204, description = "조회된 정보가 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> findProduct(@Parameter(description = "상품 ID") @PathVariable Long productId) {
        return ResponseEntity.ok()
                .body(this.productService.findProduct(productId));
    }

    @Operation(summary = "상품 등록", description = "신규 상품을 등록합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_201, description = "등록 성공",
                    content = @Content(schema = @Schema(implementation = ProductModel.class)),
                    links =  {
                            @Link(name = ResponseLinkName.SELF, description = "조회", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.LIST, description = "목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
            @ApiResponse(responseCode = ResponseCode.ERROR_409, description = "이미 등록된 상품", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidParameterException("입력 값이 올바르지 않습니다.", errors);
        }

        ProductModel createdProduct = this.productService.saveProduct(productDto);
        return ResponseEntity.created(createdProduct.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(createdProduct);
    }

    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200, description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = ProductModel.class)),
                    links =  {
                            @Link(name = ResponseLinkName.SELF, description = "조회", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.LIST, description = "목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
    })
    @PutMapping("/{productId}")
    public ResponseEntity<ProductModel> updateProduct(@Parameter(description = "상품 ID") @PathVariable Long productId,
                                                      @RequestBody @Valid ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidParameterException("입력 값이 올바르지 않습니다.", errors);
        }

        return ResponseEntity.ok()
                .body(this.productService.updateProduct(productId, productDto));
    }

    @Operation(summary = "상품 삭제", description = "상품 정보를 삭제합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200, description = "삭제 성공",
                    content = @Content(schema = @Schema(implementation = LinkFormat.class)),
                    links =  {
                            @Link(name = ResponseLinkName.LIST, description = "목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<org.springframework.hateoas.Link> deleteProduct(
            @Parameter(description = "상품 ID") @PathVariable Long productId) {
        return ResponseEntity.ok()
                .body(this.productService.deleteProduct(productId));
    }
}
