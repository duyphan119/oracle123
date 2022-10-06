package com.api.shoesshop.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.shoesshop.entities.CartItem;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.CartItemService;
import com.api.shoesshop.utils.Helper;

@Controller

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CartItemController {
        @Autowired
        private CartItemService cartItemService;

        @PostMapping("/cart-item/create")
        public ResponseEntity<String> save(HttpServletRequest req, @RequestBody CartItem body) {
                if (AuthInterceptor.isLoggedin(req) == true) {
                        try {
                                long accountId = Long.parseLong(req.getAttribute("account_id").toString());

                                CartItem cartItem = cartItemService.save(accountId, body);
                                if (cartItem != null) {
                                        return Helper.responseSuccess(cartItem);
                                }
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @PatchMapping("/cart-item/update/{id}")
        public ResponseEntity<String> update(HttpServletRequest req, @RequestBody CartItem body,
                        @PathVariable(name = "id") long id) {
                if (AuthInterceptor.isLoggedin(req) == true) {
                        try {
                                long accountId = Long.parseLong(req.getAttribute("account_id").toString());

                                CartItem cartItem = cartItemService.update(id, accountId, body);
                                if (cartItem != null) {
                                        return Helper.responseSuccess(cartItem);
                                }
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @DeleteMapping("/cart-item/delete/{id}")
        public ResponseEntity<String> delete(HttpServletRequest req, @PathVariable(name = "id") long id) {
                if (AuthInterceptor.isLoggedin(req) == true) {
                        try {
                                long accountId = Long.parseLong(req.getAttribute("account_id").toString());
                                cartItemService.delete(id, accountId);
                                return Helper.responseSussessNoData();
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

}
