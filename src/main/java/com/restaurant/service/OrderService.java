package com.restaurant.service;

import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.entity.User;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(User user, Map<Long, Integer> cart, Integer tableNum);

    void payOrder(Long orderId, boolean usePoints);

    void confirmOrder(Long orderId);

    void serveOrder(Long orderId);

    void requestRefund(Long orderId);

    void confirmRefund(Long orderId);

    List<Order> getMyOrders(Long userId);

    List<Order> getAllOrders();

    List<Order> getOrdersByStatus(String status);

    Order getOrderById(Long id);
}
