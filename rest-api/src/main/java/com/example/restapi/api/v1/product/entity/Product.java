package com.example.restapi.api.v1.product.entity;

import com.example.restapi.api.v1.product.model.ProductDto;
import com.example.restapi.api.v1.product.model.ProductModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Integer productPrice;

    @Builder
    public Product(String productName, Integer productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public void updateAll(ProductDto productDto) {
        this.productName = productDto.getProductName();
        this.productPrice = productDto.getProductPrice();
    }

    public ProductModel toModel() {
        return ProductModel.builder()
                .productId(this.productId)
                .productName(this.productName)
                .productPrice(this.productPrice)
                .build();
    }
}
