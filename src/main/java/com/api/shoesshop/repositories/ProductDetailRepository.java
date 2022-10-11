package com.api.shoesshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shoesshop.entities.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Page<ProductDetail> findBySkuContaining(String sku, Pageable pageable);

    Page<ProductDetail> findByInventory(int inventory, Pageable pageable);

    Page<ProductDetail> findByProduct_NameContaining(String productName, Pageable pageable);

    Page<ProductDetail> findByVariantValues_ValueContaining(String value, Pageable pageable);
}
