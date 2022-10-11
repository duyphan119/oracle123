package com.api.shoesshop.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shoesshop.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCreatedAtBetween(Date begin, Date end, Pageable pageable);

    List<Order> findByAccountId(long accountId);

    Page<Order> findByAccountId(long accountId, Pageable pageable);

    Page<Order> findByFullNameContaining(String fullName, Pageable pageable);

    Page<Order> findByPhoneContaining(String phone, Pageable pageable);

    Page<Order> findByProvinceContaining(String province, Pageable pageable);

    Page<Order> findByDistrictContaining(String district, Pageable pageable);

    Page<Order> findByWardContaining(String ward, Pageable pageable);

    Page<Order> findByAddressContaining(String address, Pageable pageable);
}
