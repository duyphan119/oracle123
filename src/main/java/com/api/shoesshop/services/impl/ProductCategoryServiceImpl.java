package com.api.shoesshop.services.impl;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.ProductCategory;
import com.api.shoesshop.repositories.ProductCategoryRepository;
import com.api.shoesshop.services.ProductCategoryService;
import com.api.shoesshop.utils.Helper;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory save(ProductCategory entity) {
        return productCategoryRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
        productCategoryRepository.deleteById(id);
    }

    @Override
    public Page<ProductCategory> findAll(Map<String, String> query, List<String> variantValueIds) {
        Pageable pageable = Helper.getPageable(query);
        String alias = query.get("alias");
        if (alias == null) {
            return productCategoryRepository.findAll(pageable);
        }
        String minPrice = query.get("min_price");
        String maxPrice = query.get("max_price");

        List<Long> _variantValueIds = new ArrayList<>();
        if (variantValueIds != null && variantValueIds.size() > 0) {
            for (int i = 0; i < variantValueIds.size(); i++) {
                _variantValueIds.add(Long.parseLong(variantValueIds.get(i)));
            }
            if (minPrice == null && maxPrice == null) {
                return productCategoryRepository.findByCategory_AliasAndProduct_ProductDetails_VariantValues_IdIn(alias,
                        _variantValueIds, pageable);
            } else {
                if (minPrice == null) {
                    return productCategoryRepository
                            .findByCategory_AliasAndProduct_NewPriceLessThanAndProduct_ProductDetails_VariantValues_IdIn(
                                    alias, Integer.parseInt(maxPrice), _variantValueIds,
                                    pageable);
                }
                if (maxPrice == null) {
                    return productCategoryRepository
                            .findByCategory_AliasAndProduct_NewPriceGreaterThanAndProduct_ProductDetails_VariantValues_IdIn(
                                    alias, Integer.parseInt(minPrice), _variantValueIds,
                                    pageable);
                }
                return productCategoryRepository
                        .findByCategory_AliasAndProduct_NewPriceBetweenAndProduct_ProductDetails_VariantValues_IdIn(
                                alias, Integer.parseInt(minPrice),
                                Integer.parseInt(maxPrice), _variantValueIds,
                                pageable);
            }
        } else {
            if (minPrice == null && maxPrice == null) {
                return productCategoryRepository.findByCategory_Alias(alias, pageable);
            } else {
                if (minPrice == null) {
                    return productCategoryRepository
                            .findByCategory_AliasAndProduct_NewPriceLessThan(
                                    alias, Integer.parseInt(maxPrice),
                                    pageable);
                }
                if (maxPrice == null) {
                    return productCategoryRepository
                            .findByCategory_AliasAndProduct_NewPriceGreaterThan(
                                    alias, Integer.parseInt(minPrice),
                                    pageable);
                }
                return productCategoryRepository
                        .findByCategory_AliasAndProduct_NewPriceBetween(
                                alias, minPrice == null ? 0 : Integer.parseInt(minPrice),
                                maxPrice == null ? 999999999 : Integer.parseInt(maxPrice),
                                pageable);
            }
        }

    }

    @Override
    public List<ProductCategory> saveMany(Iterable<ProductCategory> entities) {
        return productCategoryRepository.saveAll(entities);
    }

    @Override
    public List<ProductCategory> findByProduct(long productId) {
        return productCategoryRepository.findByProductId(productId);
    }

}
