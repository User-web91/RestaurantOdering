package com.restaurant.dao;

import com.restaurant.entity.Category;
import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category getById(Long id);
}
