package com.api.shoesshop.services.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.shoesshop.entities.Order;
import com.api.shoesshop.repositories.OrderRepository;
import com.api.shoesshop.services.OrderService;
import com.api.shoesshop.utils.Helper;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Page<Order> findAll(Map<String, String> query) {
        Pageable pageable = Helper.getPageable(query);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        return null;
    }

    @Override
    public Order save(long accountId, Order order) {
        order.setAccountId(accountId);
        Order newOrder = orderRepository.save(order);
        return findById(newOrder.getId());
    }

    @Override
    public Order updateStatus(Long id, String status) {
        Order order = findById(id);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> findByAccount(long accountId, Map<String, String> query) {
        Pageable pageable = Helper.getPageable(query);
        Order order = new Order();
        order.setAccountId(accountId);
        return orderRepository.findAll(Example.of(order), pageable);
    }

}
