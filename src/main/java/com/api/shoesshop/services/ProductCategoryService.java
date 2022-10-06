package com.api.shoesshop.services;

import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;

import com.api.shoesshop.entities.ProductCategory;

public interface ProductCategoryService {
    ProductCategory save(ProductCategory entity);

    void deleteById(long id);

    Page<ProductCategory> findAll(Map<String, String> query, List<String> variantValueIds);

    List<ProductCategory> saveMany(Iterable<ProductCategory> entities);

    List<ProductCategory> findByProduct(long productId);
}
