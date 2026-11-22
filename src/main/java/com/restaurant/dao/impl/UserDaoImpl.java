package com.restaurant.dao.impl;

import com.restaurant.dao.UserDao;
import com.restaurant.entity.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public User findByUsername(String username) {
        String hql = "FROM User u WHERE u.username = :username";
        Query<User> query = getSession().createQuery(hql, User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }
}
