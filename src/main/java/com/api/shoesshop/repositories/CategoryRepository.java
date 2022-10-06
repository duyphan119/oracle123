package com.api.shoesshop.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shoesshop.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByParentIdIs(Long parentId, Pageable pageable);

    Page<Category> findByParentIdIsNull(Pageable pageable);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    Page<Category> findByAliasContaining(String alias, Pageable pageable);

    Page<Category> findByParent_NameContaining(String parentName, Pageable pageable);

}
