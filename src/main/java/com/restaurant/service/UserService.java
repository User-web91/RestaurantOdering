package com.restaurant.service;

import com.restaurant.entity.User;

public interface UserService {
    User login(String username, String password);

    void register(User user);

    void updateUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

    java.util.List<User> getAllUsers();
}
