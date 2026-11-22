package com.restaurant.dao.impl;

import com.restaurant.dao.OrderDao;
import com.restaurant.entity.Order;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {
    @Override
    public List<Order> findByUserId(Long userId) {
        String hql = "FROM Order o WHERE o.user.id = :uid ORDER BY o.createTime DESC";
        Query<Order> query = getSession().createQuery(hql, Order.class);
        query.setParameter("uid", userId);
        return query.list();
    }

    @Override
    public List<Order> findByStatus(String status) {
        String hql = "FROM Order o WHERE o.status = :status ORDER BY o.createTime ASC";
        Query<Order> query = getSession().createQuery(hql, Order.class);
        query.setParameter("status", status);
        return query.list();
    }
}
