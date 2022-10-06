package com.api.shoesshop.services;

import com.api.shoesshop.entities.CartItem;

public interface CartItemService {
    CartItem save(long accountId, CartItem cartItem);

    CartItem update(long id, long accountId, CartItem cartItem);

    void delete(long id, long accountId);
}
