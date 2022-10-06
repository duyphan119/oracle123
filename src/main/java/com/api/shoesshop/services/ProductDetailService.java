package com.api.shoesshop.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.api.shoesshop.entities.ProductDetail;

public interface ProductDetailService {
    Page<ProductDetail> findAll(Map<String, String> query);

    List<ProductDetail> search(String q);

    Optional<ProductDetail> findById(long id);

    ProductDetail save(ProductDetail productDetail);

    List<ProductDetail> saveMany(Iterable<ProductDetail> entities);

    ProductDetail update(ProductDetail productDetail, long id);

    void delete(long id);

    long count();

}
