package com.restaurant.service.impl;

import com.restaurant.dao.DishDao;
import com.restaurant.dao.OrderDao;
import com.restaurant.dao.UserDao;
import com.restaurant.entity.Dish;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderItem;
import com.restaurant.entity.User;
import com.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private DishDao dishDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Order createOrder(User user, Map<Long, Integer> cart, Integer tableNum) {
        Order order = new Order();
        order.setUser(user);
        order.setTableNum(tableNum);
        order.setCreateTime(new Date());
        order.setStatus("CREATED");

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Dish dish = dishDao.getById(entry.getKey());
            if (dish != null) {
                OrderItem item = new OrderItem();
                item.setOrder(order);
                item.setDish(dish);
                item.setQuantity(entry.getValue());
                item.setUnitPrice(dish.getPrice());
                items.add(item);
                total += dish.getPrice() * entry.getValue();
            }
        }

        order.setItems(items);
        order.setTotalAmount(total);

        orderDao.save(order); // Cascade should save items
        return order;
    }

    @Override
    public void payOrder(Long orderId, boolean usePoints) {
        Order order = orderDao.getById(orderId);
        if ("CREATED".equals(order.getStatus())) {
            User user = order.getUser();
            double totalAmount = order.getTotalAmount();
            double actualPayAmount = totalAmount;
            int pointsUsed = 0;

            if (usePoints) {
                int points = user.getPoints();
                if (points > 0) {
                    double discount = points * 0.02;
                    if (discount >= totalAmount) {
                        // Points cover the whole order
                        pointsUsed = (int) Math.ceil(totalAmount / 0.02);
                        user.setPoints(points - pointsUsed);
                        actualPayAmount = 0;
                    } else {
                        // Points cover part of the order
                        pointsUsed = points;
                        user.setPoints(0);
                        actualPayAmount = totalAmount - discount;
                    }
                }
            }

            // In a real app, check user.getBalance() >= actualPayAmount
            if (user.getBalance() < actualPayAmount) {
                throw new RuntimeException("余额不足");
            }
            user.setBalance(user.getBalance() - actualPayAmount);

            // Earn points based on actual payment (1:1 ratio)
            int pointsEarned = (int) actualPayAmount;
            user.setPoints(user.getPoints() + pointsEarned);

            userDao.update(user);

            // Save payment details for refund
            order.setActualPaid(actualPayAmount);
            order.setPointsUsed(pointsUsed);
            order.setStatus("PAID");
            orderDao.update(order);
        }
    }

    @Override
    public void confirmOrder(Long orderId) {
        Order order = orderDao.getById(orderId);
        if ("PAID".equals(order.getStatus())) {
            order.setStatus("CONFIRMED"); // Kitchen preparing
            orderDao.update(order);
        }
    }



    @Override
    public void serveOrder(Long orderId) {
        Order order = orderDao.getById(orderId);
        if ("CONFIRMED".equals(order.getStatus())) {
            order.setStatus("SERVED");
            orderDao.update(order);
        }
    }

    @Override
    public void requestRefund(Long orderId) {
        Order order = orderDao.getById(orderId);
        if ("PAID".equals(order.getStatus())) {
            order.setStatus("REFUND_REQUESTED");
            orderDao.update(order);
        }
    }

    @Override
    public void confirmRefund(Long orderId) {
        Order order = orderDao.getById(orderId);
        if ("REFUND_REQUESTED".equals(order.getStatus())) {
            User user = order.getUser();
            Double refundAmount = order.getActualPaid();
            if (refundAmount != null && refundAmount > 0) {
                user.setBalance(user.getBalance() + refundAmount);
            }

            Integer pointsUsed = order.getPointsUsed();
            if (pointsUsed != null && pointsUsed > 0) {
                user.setPoints(user.getPoints() + pointsUsed);
            }

            if (refundAmount != null) {
                int pointsEarned = refundAmount.intValue();
                user.setPoints(user.getPoints() - pointsEarned);
            }

            userDao.update(user);

            order.setStatus("REFUNDED");
            orderDao.update(order);
        }
    }

    @Override
    public List<Order> getMyOrders(Long userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderDao.findByStatus(status);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.getById(id);
    }
}
