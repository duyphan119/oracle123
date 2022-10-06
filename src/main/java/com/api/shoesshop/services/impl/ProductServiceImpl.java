package com.api.shoesshop.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.Product;
import com.api.shoesshop.repositories.ProductRepository;
import com.api.shoesshop.services.ProductService;
import com.api.shoesshop.utils.Helper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Map<String, String> query) {
        Pageable pageable = Helper.getPageable(query);
        String name = query.get("name");
        String alias = query.get("alias");
        String price = query.get("price");
        String newPrice = query.get("new_price");
        String q = query.get("q");
        if (name != null) {
            return productRepository.findByNameContaining(name, pageable);
        }
        if (alias != null) {
            return productRepository.findByAliasContaining(alias, pageable);
        }
        if (price != null) {
            return productRepository.findByPrice(Integer.parseInt(price), pageable);
        }
        if (newPrice != null) {
            return productRepository.findByNewPrice(Integer.parseInt(newPrice), pageable);
        }
        if (q != null) {
            return productRepository.findByNameContainingOrAliasContaining(q, q, pageable);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> search(String q) {

        return null;
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product, long id) {
        Product exiProduct = productRepository.findById(id).get();
        if (exiProduct != null) {
            exiProduct.setName(product.getName());
            exiProduct.setAlias(product.getAlias());
            exiProduct.setThumbnail(product.getThumbnail());
            exiProduct.setPrice(product.getPrice());
            exiProduct.setNewPrice(product.getNewPrice());
        }
        return productRepository.save(exiProduct);
    }

    @Override
    public void delete(long id) {

        productRepository.deleteById(id);
    }

    @Override
    public long count() {

        return productRepository.count();
    }

}
