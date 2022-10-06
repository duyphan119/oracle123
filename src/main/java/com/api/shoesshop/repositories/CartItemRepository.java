package com.api.shoesshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shoesshop.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
