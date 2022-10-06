package com.api.shoesshop.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shoesshop.entities.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
        Page<ProductCategory> findByCategory_Alias(
                        String alias,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_NewPriceBetweenAndProduct_ProductDetails_VariantValues_IdIn(
                        String alias,
                        int minPrice, int maxPrice,
                        List<Long> variantValueIds,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_NewPriceBetween(
                        String alias,
                        int minPrice, int maxPrice,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_ProductDetails_VariantValues_IdIn(
                        String alias, List<Long> variantValueIds,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_NewPriceGreaterThanAndProduct_ProductDetails_VariantValues_IdIn(
                        String alias,
                        int minPrice,
                        List<Long> variantValueIds,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_NewPriceLessThanAndProduct_ProductDetails_VariantValues_IdIn(
                        String alias,
                        int maxPrice,
                        List<Long> variantValueIds,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_NewPriceGreaterThan(
                        String alias,
                        int minPrice,
                        Pageable pageable);

        Page<ProductCategory> findByCategory_AliasAndProduct_NewPriceLessThan(
                        String alias,
                        int maxPrice,
                        Pageable pageable);

        List<ProductCategory> findByProductId(long productId);
}
