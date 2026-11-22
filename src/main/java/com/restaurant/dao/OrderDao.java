package com.restaurant.dao;

import com.restaurant.entity.Order;
import java.util.List;

public interface OrderDao {
    void save(Order order);

    void update(Order order);

    Order getById(Long id);

    List<Order> findByUserId(Long userId);

    List<Order> findAll();

    List<Order> findByStatus(String status);
}
