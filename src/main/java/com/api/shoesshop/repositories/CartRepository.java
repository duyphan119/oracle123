package com.api.shoesshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shoesshop.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
