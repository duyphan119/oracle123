package com.api.shoesshop.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.Cart;
import com.api.shoesshop.repositories.CartRepository;
import com.api.shoesshop.services.CartService;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart findCartOfAccount(long accountId) {
        Cart cart = new Cart();
        cart.setAccountId(accountId);
        List<Cart> carts = cartRepository.findAll(Example.of(cart));
        return carts.size() > 0 ? carts.get(0) : null;
    }

}
