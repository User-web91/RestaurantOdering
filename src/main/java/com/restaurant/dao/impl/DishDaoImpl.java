package com.restaurant.dao.impl;

import com.restaurant.dao.DishDao;
import com.restaurant.entity.Dish;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishDaoImpl extends BaseDaoImpl<Dish> implements DishDao {
    @Override
    public List<Dish> searchByName(String keyword) {
        String hql = "FROM Dish d WHERE d.name LIKE :keyword AND d.status = 0";
        Query<Dish> query = getSession().createQuery(hql, Dish.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.list();
    }

    @Override
    public List<Dish> findByCategory(Long categoryId) {
        String hql = "FROM Dish d WHERE d.category.id = :cid AND d.status = 0";
        Query<Dish> query = getSession().createQuery(hql, Dish.class);
        query.setParameter("cid", categoryId);
        return query.list();
    }

    @Override
    public List<Dish> findAll() {
        return getSession().createQuery("FROM Dish d WHERE d.status = 0", Dish.class).list();
    }
}
