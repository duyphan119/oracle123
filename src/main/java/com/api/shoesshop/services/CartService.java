package com.api.shoesshop.services;

import com.api.shoesshop.entities.Cart;

public interface CartService {
    Cart findCartOfAccount(long accountId);

}
