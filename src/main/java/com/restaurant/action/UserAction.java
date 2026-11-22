package com.restaurant.action;

import com.restaurant.entity.User;
import com.restaurant.service.UserService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@Namespace("/user")
public class UserAction extends BaseAction {
    @Autowired
    private UserService userService;
    private String username;
    private String password;
    private String role;
    private User user;

    @Action(value = "login", results = {
            @Result(name = "success", type = "redirect", location = "/index.jsp"),
            @Result(name = "input", location = "/WEB-INF/jsp/login.jsp"),
            @Result(name = "error", location = "/WEB-INF/jsp/login.jsp")
    })
    public String login() {
        User u = userService.login(username, password);
        if (u != null) {
            session.put("user", u);
            return SUCCESS;
        } else {
            addActionError("用户名或密码错误");
            return ERROR;
        }
    }

    @Action(value = "register", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/login.jsp"),
            @Result(name = "input", location = "/WEB-INF/jsp/register.jsp")
    })
    public String register() {
        if (user != null) {
            if ("admin".equals(user.getRole())) {
            }
            // default role if not set
            if (user.getRole() == null)
                user.setRole("customer");
            try {
                userService.register(user);
                return SUCCESS;
            } catch (RuntimeException e) {
                addActionError(e.getMessage());
                return INPUT;
            }
        }
        return INPUT;
    }

    @Action(value = "profile", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/profile.jsp")
    })
    public String profile() {
        User u = (User) session.get("user");
        if (u != null) {
            // refresh from db to get latest info
            user = userService.getUserById(u.getId());
            session.put("user", user);
        }
        return SUCCESS;
    }

    @Action(value = "updateProfile", results = {
            @Result(name = "success", type = "redirect", location = "profile")
    })
    public String updateProfile() {
        User currentUser = (User) session.get("user");
        if (currentUser != null && user != null) {
            // Update fields
            currentUser.setUsername(user.getUsername());
            currentUser.setPhone(user.getPhone());
            currentUser.setPassword(user.getPassword());

            userService.updateUser(currentUser);
            session.put("user", currentUser); // update session
        }
        return SUCCESS;
    }

    @Action(value = "logout", results = {
            @Result(name = "success", type = "redirect", location = "/index.jsp")
    })
    public String logout() {
        session.clear();
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
