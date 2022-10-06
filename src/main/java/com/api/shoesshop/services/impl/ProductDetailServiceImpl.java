package com.api.shoesshop.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.ProductDetail;
import com.api.shoesshop.repositories.ProductDetailRepository;
import com.api.shoesshop.services.ProductDetailService;
import com.api.shoesshop.utils.Helper;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Page<ProductDetail> findAll(Map<String, String> query) {
        Pageable pageable = Helper.getPageable(query);

        return productDetailRepository.findAll(pageable);
    }

    @Override
    public List<ProductDetail> search(String q) {

        return null;
    }

    @Override
    public Optional<ProductDetail> findById(long id) {
        return productDetailRepository.findById(id);
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail update(ProductDetail productDetail, long id) {
        ProductDetail exiProductDetail = productDetailRepository.findById(id).get();
        if (exiProductDetail != null) {
            exiProductDetail.setTitle(productDetail.getTitle());
            exiProductDetail.setSku(productDetail.getSku());
            exiProductDetail.setThumbnail(productDetail.getThumbnail());
            exiProductDetail.setInventory(productDetail.getInventory());
            exiProductDetail.setProductId(productDetail.getProductId());
            exiProductDetail.setWeight(productDetail.getWeight());
        }
        return productDetailRepository.save(exiProductDetail);
    }

    @Override
    public void delete(long id) {

        productDetailRepository.deleteById(id);
    }

    @Override
    public long count() {

        return productDetailRepository.count();
    }

    @Override
    public List<ProductDetail> saveMany(Iterable<ProductDetail> entities) {
        return productDetailRepository.saveAll(entities);
    }
}
