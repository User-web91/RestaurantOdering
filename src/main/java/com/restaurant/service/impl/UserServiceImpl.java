package com.restaurant.service.impl;

import com.restaurant.dao.UserDao;
import com.restaurant.entity.User;
import com.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public void register(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("此用户名已存在");
        }
        if (user.getPhone() != null && !user.getPhone().matches("\\d+")) {
            throw new RuntimeException("无效电话号码");
        }
        userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }

    @Override
    public java.util.List<User> getAllUsers() {
        return userDao.findAll();
    }
}
