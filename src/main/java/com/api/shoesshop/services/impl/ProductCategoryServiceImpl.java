package com.api.shoesshop.services.impl;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.ProductCategory;
import com.api.shoesshop.repositories.ProductCategoryRepository;
import com.api.shoesshop.services.ProductCategoryService;
import com.api.shoesshop.utils.Helper;

import org.springframework.data.domain.Sort;

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
        String page = query.get("p");
        String pageSize = query.get("limit");
        String sortBy = query.get("sort_by");
        String sortType = query.get("sort_type");
        if (sortBy == null) {
            sortBy = "id";
        }
        if (sortBy.equals("name")) {
            sortBy = "product.name";
        }
        if (sortBy.equals("price")) {
            sortBy = "product.salePrice";
        }
        Sort sort = Sort.by(sortBy);
        if (sortType == null || sortType.equals("desc")) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(page == null ? 0 : Integer.parseInt(page) - 1,
                pageSize == null ? 10 : Integer.parseInt(pageSize), sort);
        String alias = query.get("category_alias");
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
            // if (minPrice == null && maxPrice == null) {
            // return
            // productCategoryRepository.findByCategory_AliasAndProduct_ProductDetails_VariantValues_IdIn(alias,
            // _variantValueIds, pageable);
            // } else {
            // if (minPrice == null) {
            // return productCategoryRepository
            // .findByCategory_AliasAndProduct_SalePriceLessThanAndProduct_ProductDetails_VariantValues_IdIn(
            // alias, Integer.parseInt(maxPrice), _variantValueIds,
            // pageable);
            // }
            // if (maxPrice == null) {
            // return productCategoryRepository
            // .findByCategory_AliasAndProduct_SalePriceGreaterThanAndProduct_ProductDetails_VariantValues_IdIn(
            // alias, Integer.parseInt(minPrice), _variantValueIds,
            // pageable);
            // }
            // return productCategoryRepository
            // .findByCategory_AliasAndProduct_SalePriceBetweenAndProduct_ProductDetails_VariantValues_IdIn(
            // alias, Integer.parseInt(minPrice),
            // Integer.parseInt(maxPrice), _variantValueIds,
            // pageable);
            // }
            // } else {
            // if (minPrice == null && maxPrice == null) {
            // return productCategoryRepository.findByCategory_Alias(alias, pageable);
            // } else {
            // if (minPrice == null) {
            // return productCategoryRepository
            // .findByCategory_AliasAndProduct_SalePriceLessThan(
            // alias, Integer.parseInt(maxPrice),
            // pageable);
            // }
            // if (maxPrice == null) {
            // return productCategoryRepository
            // .findByCategory_AliasAndProduct_SalePriceGreaterThan(
            // alias, Integer.parseInt(minPrice),
            // pageable);
            // }
            // return productCategoryRepository
            // .findByCategory_AliasAndProduct_SalePriceBetween(
            // alias, minPrice == null ? 0 : Integer.parseInt(minPrice),
            // maxPrice == null ? 999999999 : Integer.parseInt(maxPrice),
            // pageable);
            // }
        }
        return null;
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
