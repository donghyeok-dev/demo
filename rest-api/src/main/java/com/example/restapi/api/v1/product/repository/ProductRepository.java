package com.example.restapi.api.v1.product.repository;

import com.example.restapi.api.v1.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductNameAndProductPrice(String productName, Integer productPrice);
}
