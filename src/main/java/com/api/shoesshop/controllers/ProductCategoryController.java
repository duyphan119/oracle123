package com.api.shoesshop.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.shoesshop.entities.ProductCategory;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.ProductCategoryService;
import com.api.shoesshop.types.FindAll;
import com.api.shoesshop.utils.Helper;

@Controller

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/product/category/read")
    public ResponseEntity<String> findByCategory(@RequestParam Map<String, String> query,
            @RequestParam(name = "variant_value_ids", required = false) List<String> variantValueIds) {
        try {
            Page<ProductCategory> page = productCategoryService.findAll(query, variantValueIds);
            return Helper.responseSuccess(new FindAll<>(page.getContent(), page.getTotalElements()));
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
    }

    @GetMapping("/category/product/read/{id}")
    public ResponseEntity<String> findByProduct(@PathVariable(name = "id") long productId) {
        try {
            List<ProductCategory> list = productCategoryService.findByProduct(productId);
            return Helper.responseSuccess(list);
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();
        }
    }

    @PostMapping("/product/category/create")
    public ResponseEntity<String> save(HttpServletRequest req, @RequestBody ProductCategory body) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                ProductCategory productCategory = productCategoryService.save(body);
                return Helper.responseCreated(productCategory);
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }
        return Helper.responseUnauthorized();
    }

    @PostMapping("/product/category/create/many")
    public ResponseEntity<String> saveMany(HttpServletRequest req, @RequestBody Iterable<ProductCategory> body) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                List<ProductCategory> productCategory = productCategoryService.saveMany(body);
                return Helper.responseCreated(productCategory);
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }
        return Helper.responseUnauthorized();
    }

    @DeleteMapping("/product/category/delete/{id}")
    public ResponseEntity<String> delete(HttpServletRequest req, @PathVariable long id) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                productCategoryService.deleteById(id);
                return Helper.responseSussessNoData();
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }
        return Helper.responseUnauthorized();
    }
}
