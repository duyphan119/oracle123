package com.api.shoesshop.services;

import java.util.Map;
import java.util.List;

import org.springframework.data.domain.Page;

import com.api.shoesshop.entities.Category;

public interface CategoryService {
    Category save(Category entity);

    Page<Category> findAll(Map<String, String> query);

    void delete(Long id);

    Category update(long id, Category entity);

    Category findById(long id);

    List<Category> findAll();
}
