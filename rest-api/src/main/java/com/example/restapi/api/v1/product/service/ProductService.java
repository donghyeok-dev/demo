package com.example.restapi.api.v1.product.service;

import com.example.restapi.api.v1.product.entity.Product;
import com.example.restapi.api.v1.product.model.ProductDto;
import com.example.restapi.api.v1.product.model.ProductModel;
import com.example.restapi.api.v1.product.model.ProductModelAssembler;
import com.example.restapi.api.v1.product.repository.ProductRepository;
import com.example.restapi.global.error.exception.ConflictDataException;
import com.example.restapi.global.error.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 Service
 * @author kdh
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductModelAssembler productModelAssembler;

    /*
        상품 목록 조회
     */
    @Transactional(readOnly = true)
    public CollectionModel<ProductModel> findAllProduct() {
        return this.productModelAssembler.toCollectionModel(this.productRepository.findAll());
    }

    /*
        상품 조회
     */
    @Transactional(readOnly = true)
    public ProductModel findProduct(Long productId) {
        return productModelAssembler.toModelDetail(this.productRepository.findById(productId)
                .orElseThrow(NotFoundDataException::new));
    }

    /*
        상품 등록
     */
    @Transactional
    public ProductModel saveProduct(ProductDto productDto) {
        if(this.productRepository.existsByProductNameAndProductPrice(productDto.getProductName(), 
                productDto.getProductPrice())) {
            throw new ConflictDataException("이미 등록된 상품입니다.");
        }

        return this.productModelAssembler.toModelUpdate(this.productRepository.save(productDto.toEntity()));
    }

    /*
        상품 수정
     */
    @Transactional
    public ProductModel updateProduct(Long productId, ProductDto productDto) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new EmptyResultDataAccessException("존재하지 않는 상품입니다.", 1));

        product.updateAll(productDto);

        return this.productModelAssembler.toModelUpdate(product);
    }

    @Transactional
    public Link deleteProduct(Long productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new EmptyResultDataAccessException("존재하지 않는 상품입니다.", 1));

        this.productRepository.delete(product);

        return this.productModelAssembler.toModelDelete();
    }

}
