package com.restaurant.action;

import com.restaurant.entity.Dish;
import com.restaurant.service.DishService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope("prototype")
@Namespace("/dish")
public class DishAction extends BaseAction {

    @Autowired
    private DishService dishService;

    private List<Dish> dishes;
    private String keyword;
    private Long categoryId;


    private Dish dish;
    private Long id;

    private List<com.restaurant.entity.Category> categories;

    @Action(value = "list", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/menu.jsp")
    })
    public String list() {
        categories = dishService.getAllCategories();
        if (keyword != null && !keyword.isEmpty()) {
            dishes = dishService.searchDishes(keyword);
        } else if (categoryId != null) {
            dishes = dishService.getDishesByCategory(categoryId);
        } else {
            dishes = dishService.getAllDishes();
        }
        return SUCCESS;
    }

    @Action(value = "manage", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/manage_dishes.jsp")
    })
    public String manage() {
        dishes = dishService.getAllDishes();
        categories = dishService.getAllCategories();
        return SUCCESS;
    }

    // File Upload properties
    private java.io.File image;
    private String imageFileName;
    private String imageContentType;

    @Action(value = "save", results = {
            @Result(name = "success", type = "redirect", location = "manage")
    })
    public String save() {
        if (image != null) {
            try {
                String path = org.apache.struts2.ServletActionContext.getServletContext().getRealPath("/images");
                java.io.File file = new java.io.File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }

                // 使用时间戳区分
                String fileName = System.currentTimeMillis() + "_" + imageFileName;
                org.apache.commons.io.FileUtils.copyFile(image, new java.io.File(file, fileName));

                dish.setImageUrl("images/" + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 手动分类
        if (categoryId != null) {
            com.restaurant.entity.Category c = new com.restaurant.entity.Category();
            c.setId(categoryId);
            dish.setCategory(c);
        }

        if (dish.getId() == null) {
            dishService.addDish(dish);
        } else {

            if (image == null) {
                Dish existing = dishService.getDishById(dish.getId());
                if (existing != null) {
                    dish.setImageUrl(existing.getImageUrl());
                }
            }
            dishService.updateDish(dish);
        }
        return SUCCESS;
    }

    @Action(value = "edit", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/manage_dishes.jsp")
    })
    public String edit() {
        dish = dishService.getDishById(id);
        dishes = dishService.getAllDishes(); // Refresh list to keep table populated
        categories = dishService.getAllCategories();
        return SUCCESS;
    }

    @Action(value = "delete", results = {
            @Result(name = "success", type = "redirect", location = "manage")
    })
    public String delete() {
        dishService.deleteDish(id);
        return SUCCESS;
    }


    public List<Dish> getDishes() {
        return dishes;
    }

    public List<com.restaurant.entity.Category> getCategories() {
        return categories;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImage(java.io.File image) {
        this.image = image;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }
}
