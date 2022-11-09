package com.api.shoesshop.controllers;

import java.util.List;
import java.util.Map;

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

import com.api.shoesshop.entities.Category;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.CategoryService;
import com.api.shoesshop.types.FindAll;
import com.api.shoesshop.utils.Helper;

@Controller

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/category")
    public ResponseEntity<String> findAll(@RequestParam Map<String, String> query) {
        try {
            categoryService.findAll(query);
            Page<Category> page = categoryService.findAll(query);
            return Helper.responseSuccess(new FindAll<>(page.getContent(), page.getTotalElements()));
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();

        }
    }

    @GetMapping("/api/category/all")
    public ResponseEntity<String> findAll(HttpServletRequest req) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                List<Category> list = categoryService.findAll();
                return Helper.responseSuccess(list);
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();

            }
        }
        return Helper.responseUnauthorized();

    }

    @GetMapping("/api/category/{id}")
    public ResponseEntity<String> findById(@PathVariable long id) {
        try {
            Category category = categoryService.findById(id);
            return Helper.responseSuccess(category);
        } catch (Exception e) {
            System.out.println(e);
            return Helper.responseError();

        }
    }

    @PostMapping("/api/category")
    public ResponseEntity<String> save(HttpServletRequest req, @RequestBody Category body) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                Category createdCategory = categoryService.save(body);
                if (createdCategory != null) {
                    return Helper.responseCreated(createdCategory);
                }
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();

    }

    @PatchMapping("/api/category/{id}")
    public ResponseEntity<String> update(HttpServletRequest req, @PathVariable long id, @RequestBody Category body) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                Category createdCategory = categoryService.update(id, body);
                if (createdCategory != null) {
                    return Helper.responseCreated(createdCategory);
                }
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
            return Helper.responseError();
        }
        return Helper.responseUnauthorized();
    }

    @DeleteMapping("/api/category/{id}")
    public ResponseEntity<String> delete(HttpServletRequest req, @PathVariable long id) {
        if (AuthInterceptor.isAdmin(req) == true) {
            try {
                categoryService.delete(id);
                return Helper.responseSuccessNoData();
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }
        return Helper.responseUnauthorized();

    }

}
