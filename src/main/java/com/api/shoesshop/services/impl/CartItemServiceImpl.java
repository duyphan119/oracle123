package com.api.shoesshop.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.Cart;
import com.api.shoesshop.entities.CartItem;
import com.api.shoesshop.repositories.CartItemRepository;
import com.api.shoesshop.repositories.CartRepository;
import com.api.shoesshop.services.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartItem save(long accountId, CartItem cartItem) {
        Cart reqCart = new Cart();
        reqCart.setAccountId(accountId);
        List<Cart> carts = cartRepository.findAll(Example.of(reqCart));
        if (carts.size() > 0) {
            cartItem.setCartId(carts.get(0).getId());
            CartItem saved = cartItemRepository.save(cartItem);
            return cartItemRepository.findById(saved.getId()).get();
        }

        return null;
    }

    @Override
    public CartItem update(long id, long accountId, CartItem cartItem) {
        System.out.println("update: " + id);
        System.out.println("quantity: " + cartItem.getQuantity());
        Optional<CartItem> optional = cartItemRepository.findById(id);
        if (optional.isPresent()) {
            CartItem newCartItem = optional.get();
            newCartItem.setQuantity(cartItem.getQuantity());
            Cart reqCart = new Cart();
            reqCart.setAccountId(accountId);

            List<Cart> carts = cartRepository.findAll(Example.of(reqCart));
            if (carts.size() > 0) {
                if (newCartItem.getCartId() == carts.get(0).getId()) {
                    return cartItemRepository.save(newCartItem);
                }
            }
        }
        return null;
    }

    @Override
    public void delete(long id, long accountId) {
        Cart reqCart = new Cart();
        reqCart.setAccountId(accountId);
        List<Cart> carts = cartRepository.findAll(Example.of(reqCart));
        Optional<CartItem> optional = cartItemRepository.findById(id);
        if (optional.isPresent()) {
            if (carts.size() > 0) {
                if (optional.get().getCartId() == carts.get(0).getId()) {
                    cartItemRepository.deleteById(id);
                }
            }
        }
    }
}
