package com.api.shoesshop.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.api.shoesshop.entities.ProductDetail;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.ProductDetailService;
import com.api.shoesshop.types.FindAll;
import com.api.shoesshop.utils.Helper;
import com.google.gson.Gson;

@Controller

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping(value = "/product-detail/read")
    public ResponseEntity<String> findAll(@RequestParam Map<String, String> query) {
        try {
            Page<ProductDetail> page = productDetailService.findAll(query);
            return Helper.responseSuccess(new FindAll<>(page.getContent(), page.getTotalElements()));
        } catch (Exception e) {
            return Helper.responseError();
        }
    }

    @GetMapping(value = "/product-detail/read/{id}")
    public ResponseEntity<String> findById(@PathVariable(name = "id") long id) {

        try {
            Optional<ProductDetail> ProductDetail = productDetailService.findById(id);
            return Helper.responseSuccess(ProductDetail.get());
        } catch (Exception e) {
        }
        return Helper.responseError();
    }

    @PostMapping(value = "/product-detail/create")
    public ResponseEntity<String> save(HttpServletRequest req, @RequestBody ProductDetail body) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                System.out.println(new Gson().toJson(body));
                ProductDetail productDetail = productDetailService.save(body);
                return Helper.responseCreated(productDetail);
            } catch (Exception e) {
                System.out.println(e);
            }
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();
    }

    @PostMapping(value = "/product-detail/create/many")
    public ResponseEntity<String> saveMany(HttpServletRequest req, @RequestBody Iterable<ProductDetail> body) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                System.out.println(new Gson().toJson(body));
                List<ProductDetail> productDetail = productDetailService.saveMany(body);
                return Helper.responseCreated(productDetail);
            } catch (Exception e) {
                System.out.println(e);
            }
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();
    }

    @PatchMapping(value = "/product-detail/update/{id}")
    public ResponseEntity<String> update(HttpServletRequest req, @RequestBody ProductDetail body,
            @PathVariable(name = "id") long id) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                ProductDetail productDetail = productDetailService.update(body, id);
                return Helper.responseSuccess(productDetail);
            } catch (Exception e) {
                System.out.println(e);
            }
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();
    }

    @DeleteMapping(value = "/product-detail/delete/{id}")
    public ResponseEntity<String> delete(HttpServletRequest req, @PathVariable(name = "id") long id) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                productDetailService.delete(id);
                return Helper.responseSussessNoData();
            } catch (Exception e) {
            }
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();
    }

}
