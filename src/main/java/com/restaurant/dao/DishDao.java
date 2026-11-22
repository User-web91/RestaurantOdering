package com.restaurant.dao;

import com.restaurant.entity.Dish;
import java.util.List;

public interface DishDao {
    void save(Dish dish);

    void update(Dish dish);

    void delete(Long id);

    Dish getById(Long id);

    List<Dish> findAll();

    List<Dish> searchByName(String keyword);

    List<Dish> findByCategory(Long categoryId);
}
