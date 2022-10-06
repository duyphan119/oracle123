package com.api.shoesshop.controllers;

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

import com.api.shoesshop.entities.Order;
import com.api.shoesshop.interceptors.AuthInterceptor;
import com.api.shoesshop.services.OrderService;
import com.api.shoesshop.types.FindAll;
import com.api.shoesshop.utils.Helper;

@Controller

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OrderController {

        @Autowired
        private OrderService orderService;

        @GetMapping("/order/read")
        public ResponseEntity<String> findAll(HttpServletRequest req, @RequestParam Map<String, String> query) {
                if (AuthInterceptor.isAdmin(req) == true) {
                        try {
                                Page<Order> page = orderService.findAll(query);
                                return Helper.responseSuccess(
                                                new FindAll<>(page.getContent(), page.getTotalElements()));
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @GetMapping("/order/account/read")
        public ResponseEntity<String> findByAccount(HttpServletRequest req, @RequestParam Map<String, String> query) {
                if (AuthInterceptor.isLoggedin(req) == true) {
                        try {
                                long accountId = Long.parseLong(req.getAttribute("account_id").toString());
                                Page<Order> page = orderService.findByAccount(accountId, query);
                                return Helper.responseSuccess(
                                                new FindAll<>(page.getContent(), page.getTotalElements()));
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @GetMapping("/order/read/{id}")
        public ResponseEntity<String> findById(HttpServletRequest req, @PathVariable Long id) {
                if (AuthInterceptor.isAdmin(req) == true) {
                        try {
                                Order order = orderService.findById(id);
                                if (order != null)
                                        return Helper.responseSuccess(order);

                                return Helper.responseError();
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @PostMapping("/order/account/create")
        public ResponseEntity<String> save(HttpServletRequest req, @RequestBody Order body) {
                if (AuthInterceptor.isCustomer(req) == true) {
                        try {
                                if (req.getAttribute("account_id") != null) {
                                        long accountId = Long.parseLong(req.getAttribute("account_id").toString());
                                        Order order = orderService.save(accountId, body);
                                        if (order != null)
                                                return Helper.responseCreated(order);
                                }
                                return Helper.responseUnauthorized();
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @PostMapping("/order/create")
        public ResponseEntity<String> saveByAdmin(HttpServletRequest req, @RequestBody Order body) {
                if (AuthInterceptor.isAdmin(req) == true) {
                        try {
                                Order order = orderService.save(body.getAccountId(), body);
                                if (order != null)
                                        return Helper.responseCreated(order);
                                return Helper.responseUnauthorized();
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @PatchMapping("/order/update/{id}")
        public ResponseEntity<String> update(HttpServletRequest req, @RequestBody Order body, @PathVariable Long id) {
                if (AuthInterceptor.isAdmin(req) == true) {
                        try {
                                Order order = orderService.updateStatus(id, body.getStatus());
                                if (order != null)
                                        return Helper.responseSuccess(order);

                                return Helper.responseUnauthorized();
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }

        @DeleteMapping("/order/delete/{id}")
        public ResponseEntity<String> update(HttpServletRequest req, @PathVariable Long id) {
                if (AuthInterceptor.isCustomer(req) == true || AuthInterceptor.isAdmin(req) == true) {
                        try {
                                Order order = orderService.findById(id);
                                if (order != null) {
                                        if (order.getStatus().equals("Đang xử lý") == true) {
                                                orderService.delete(id);
                                                return Helper.responseSussessNoData();
                                        }
                                }
                        } catch (Exception e) {
                                System.out.println(e);
                                return Helper.responseError();
                        }
                }
                return Helper.responseUnauthorized();
        }
}
