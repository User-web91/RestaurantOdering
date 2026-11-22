package com.restaurant.service;

import com.restaurant.entity.Category;
import com.restaurant.entity.Dish;
import java.util.List;

public interface DishService {
    List<Dish> getAllDishes();

    List<Dish> searchDishes(String name);

    List<Dish> getDishesByCategory(Long categoryId);

    Dish getDishById(Long id);

    void addDish(Dish dish);

    void updateDish(Dish dish);

    void deleteDish(Long id);

    List<Category> getAllCategories();
}
