package com.api.shoesshop.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.Coupon;
import com.api.shoesshop.entities.Order;
import com.api.shoesshop.repositories.CouponRepository;
import com.api.shoesshop.repositories.OrderRepository;
import com.api.shoesshop.services.CouponService;
import com.api.shoesshop.utils.Helper;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Coupon save(Coupon entity) {

        return couponRepository.save(entity);
    }

    @Override
    public Coupon update(long id, Coupon entity) {
        Coupon coupon = findById(id);
        if (coupon != null) {
            return couponRepository.save(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Page<Coupon> findAll(Map<String, String> query) {
        Page<Coupon> page = couponRepository.findAll(Helper.getPageable(query));
        return page;
    }

    @Override
    public Coupon findById(long id) {
        Optional<Coupon> optional = couponRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Coupon check(long accountId, int price, String code) {
        List<Order> orders = orderRepository.findByAccountId(accountId);
        for (int i = 0; i < orders.size(); i++) {
            List<Coupon> coupons = orders.get(i).getCoupons();
            for (int j = 0; j < coupons.size(); j++) {
                if (coupons.get(j).getCode().equals(code) == true) {
                    return null;
                }
            }
        }
        Optional<Coupon> optional = couponRepository.findByCodeAndMinPriceLessThanEqual(code,
                price);
        if (optional.isPresent()) {
            if (optional.get().getEndDate().compareTo(new Date()) == 1)
                return optional.get();
        }
        return null;
    }
}
