package com.api.shoesshop.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.api.shoesshop.entities.Product;

public interface ProductService {
    Page<Product> findAll(Map<String, String> query);

    List<Product> search(String q);

    Optional<Product> findById(long id);

    Product save(Product product);

    Product update(Product product, long id);

    void delete(long id);

    long count();

}
