package com.example.restapi.api.v1.product.model;

import com.example.restapi.api.v1.product.controller.ProductController;
import com.example.restapi.api.v1.product.entity.Product;
import com.example.restapi.global.util.CommonApiCode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 상품 Model Assembler
 * @author kdh
 */
@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, ProductModel> {
    private Errors errorsDummy;

    public ProductModelAssembler() {
        this.errorsDummy = new BeanPropertyBindingResult(new Object(), "dummy");
    }

    @Override
    public ProductModel toModel(Product product) {
        return product.toModel()
                .add(getSelfLink(product.getProductId()));
    }

    public ProductModel toModelDetail(Product product) {
        return product.toModel()
                .add(getSelfLink(product.getProductId()))
                .add(getUpdateLink(product.getProductId()))
                .add(getDeleteLink(product.getProductId()))
                .add(getListLink(false));
    }

    public ProductModel toModelUpdate(Product product) {
        return product.toModel()
                .add(getSelfLink(product.getProductId()))
                .add(getListLink(false));
    }

    public Link toModelDelete() {
        return getListLink(false);
    }

    @Override
    public CollectionModel<ProductModel> toCollectionModel(Iterable<? extends Product> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(getListLink(true))
                .add(getCreateLink());
    }

    private Link getSelfLink(Long id) {
        return linkTo(methodOn(ProductController.class)
                .findProduct(id))
                .withSelfRel()
                .withType(CommonApiCode.HttpMethod.GET);
    }

    private Link getListLink(boolean self) {
        return self
                ? linkTo(methodOn(ProductController.class)
                .getAllProduct())
                .withSelfRel()
                .withType(CommonApiCode.HttpMethod.GET)
                : linkTo(methodOn(ProductController.class)
                .getAllProduct())
                .withRel(CommonApiCode.ResponseLinkName.LIST)
                .withType(CommonApiCode.HttpMethod.GET);
    }

    private Link getCreateLink() {
        return linkTo(methodOn(ProductController.class)
                .createProduct(null, this.errorsDummy))
                .withRel(CommonApiCode.ResponseLinkName.CREATE)
                .withType(CommonApiCode.HttpMethod.POST);
    }

    private Link getUpdateLink(Long id) {
        return linkTo(methodOn(ProductController.class)
                .updateProduct(id, null, this.errorsDummy))
                .withRel(CommonApiCode.ResponseLinkName.UPDATE)
                .withType(CommonApiCode.HttpMethod.PUT);
    }

    private Link getDeleteLink(Long id) {
        return linkTo(methodOn(ProductController.class)
                .deleteProduct(id))
                .withRel(CommonApiCode.ResponseLinkName.DELETE)
                .withType(CommonApiCode.HttpMethod.DELETE);
    }

}
