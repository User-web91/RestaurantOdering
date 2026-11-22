package com.restaurant.service.impl;

import com.restaurant.dao.CategoryDao;
import com.restaurant.dao.DishDao;
import com.restaurant.entity.Category;
import com.restaurant.entity.Dish;
import com.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Dish> getAllDishes() {
        return dishDao.findAll();
    }

    @Override
    public List<Dish> searchDishes(String name) {
        return dishDao.searchByName(name);
    }

    @Override
    public List<Dish> getDishesByCategory(Long categoryId) {
        return dishDao.findByCategory(categoryId);
    }

    @Override
    public Dish getDishById(Long id) {
        return dishDao.getById(id);
    }

    @Override
    public void addDish(Dish dish) {
        dishDao.save(dish);
    }

    @Override
    public void updateDish(Dish dish) {
        dishDao.update(dish);
    }

    @Override
    public void deleteDish(Long id) {
        // Logic Delete
        Dish dish = dishDao.getById(id);
        if (dish != null) {
            dish.setStatus(1); // Sold out / Deleted
            dishDao.update(dish);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }
}
