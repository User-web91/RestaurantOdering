package com.restaurant.dao;

import com.restaurant.entity.User;

public interface UserDao {
    void save(User user);

    void update(User user);

    void delete(Long id);

    User getById(Long id);

    User findByUsername(String username);

    java.util.List<User> findAll();
}
