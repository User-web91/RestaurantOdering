package com.restaurant.action;

import com.restaurant.entity.User;
import com.restaurant.service.UserService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@Scope("prototype")
@Namespace("/admin")
public class AdminAction extends BaseAction {
    @Autowired
    private UserService userService;
    private List<User> users;
    private Long id;

    @Action(value = "users", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/manage_users.jsp")
    })
    public String listUsers() {
        users = userService.getAllUsers();
        return SUCCESS;
    }

    @Action(value = "deleteUser", results = {
            @Result(name = "success", type = "redirect", location = "users")
    })
    public String deleteUser() {
        userService.deleteUser(id);
        return SUCCESS;
    }

    private User user;

    @Action(value = "addUser", results = {
            @Result(name = "success", type = "redirect", location = "users")
    })
    public String addUser() {
        if (user != null) {

            try {
                userService.register(user);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return SUCCESS;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }
}