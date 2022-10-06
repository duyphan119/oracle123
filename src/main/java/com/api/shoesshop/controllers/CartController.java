package com.api.shoesshop.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.api.shoesshop.entities.Cart;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.CartService;
import com.api.shoesshop.utils.Helper;

@Controller

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart/account/read")
    public ResponseEntity<String> findCartByAccount(HttpServletRequest req) {
        if (AuthInterceptor.isLoggedin(req) == true) {
            try {
                long accountId = Long.parseLong(req.getAttribute("account_id").toString());
                Cart cart = cartService.findCartOfAccount(accountId);
                if (cart != null) {
                    return Helper.responseSuccess(cart);
                }
            } catch (Exception e) {
                System.out.println(e);
                return Helper.responseError();
            }
        }
        return Helper.responseUnauthorized();
    }
}
