package com.restaurant.action;

import com.restaurant.entity.Dish;
import com.restaurant.entity.Order;
import com.restaurant.entity.User;
import com.restaurant.service.DishService;
import com.restaurant.service.OrderService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@Namespace("/order")
public class OrderAction extends BaseAction {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DishService dishService;

    private Long dishId;
    private Integer quantity;
    private Integer tableNum;
    private List<Order> orders;
    private Long orderId;

    // 购物车存于session， Map<dishId, quantity>

    @Action(value = "addToCart", results = {
            @Result(name = "success", type = "redirect", location = "/dish/list")
    })
    public String addToCart() {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.get("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.put("cart", cart);
        }
        cart.put(dishId, cart.getOrDefault(dishId, 0) + (quantity == null ? 1 : quantity));
        return SUCCESS;
    }

    private List<CartItem> cartItems;
    private Double cartTotal;

    @Action(value = "viewCart", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/cart.jsp")
    })
    public String viewCart() {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.get("cart");
        if (cart != null && !cart.isEmpty()) {
            cartItems = new java.util.ArrayList<>();
            cartTotal = 0.0;
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                Dish dish = dishService.getDishById(entry.getKey());
                if (dish != null) {
                    CartItem item = new CartItem();
                    item.setDish(dish);
                    item.setQuantity(entry.getValue());
                    item.setSubtotal(dish.getPrice() * entry.getValue());
                    cartItems.add(item);
                    cartTotal += item.getSubtotal();
                }
            }
        }
        return SUCCESS;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Double getCartTotal() {
        return cartTotal;
    }

    public static class CartItem {
        private Dish dish;
        private Integer quantity;
        private Double subtotal;

        public Dish getDish() {
            return dish;
        }

        public void setDish(Dish dish) {
            this.dish = dish;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }
    }

    @Action(value = "placeOrder", results = {
            @Result(name = "success", type = "redirect", location = "myOrders"),
            @Result(name = "login", location = "/WEB-INF/jsp/login.jsp")
    })
    public String placeOrder() {
        User user = (User) session.get("user");
        if (user == null)
            return "login";

        Map<Long, Integer> cart = (Map<Long, Integer>) session.get("cart");
        if (cart != null && !cart.isEmpty()) {
            orderService.createOrder(user, cart, tableNum);
            session.remove("cart");
        }
        return SUCCESS;
    }

    @Action(value = "myOrders", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/my_orders.jsp"),
            @Result(name = "login", location = "/WEB-INF/jsp/login.jsp")
    })
    public String myOrders() {
        User user = (User) session.get("user");
        if (user == null)
            return "login";
        orders = orderService.getMyOrders(user.getId());
        return SUCCESS;
    }

    private boolean usePoints;
    private Order currentOrder; // For payment page display

    @Action(value = "toPay", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/payment.jsp")
    })
    public String toPay() {
        currentOrder = orderService.getOrderById(orderId);
        User u = (User) session.get("user");
        return SUCCESS;
    }

    @Action(value = "pay", results = {
            @Result(name = "success", type = "redirect", location = "myOrders"),
            @Result(name = "input", location = "/WEB-INF/jsp/payment.jsp")
    })
    public String pay() {
        try {
            orderService.payOrder(orderId, usePoints);
            return SUCCESS;
        } catch (RuntimeException e) {
            addActionError(e.getMessage());
            // Reload order for the view
            currentOrder = orderService.getOrderById(orderId);
            return "input";
        }
    }

    public boolean isUsePoints() {
        return usePoints;
    }

    public void setUsePoints(boolean usePoints) {
        this.usePoints = usePoints;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    // Waiter Actions
    @Action(value = "manage", results = {
            @Result(name = "success", location = "/WEB-INF/jsp/manage_orders.jsp")
    })
    public String manage() {
        orders = orderService.getAllOrders();
        return SUCCESS;
    }

    @Action(value = "confirm", results = { @Result(name = "success", type = "redirect", location = "manage") })
    public String confirm() {
        orderService.confirmOrder(orderId);
        return SUCCESS;
    }

    @Action(value = "serve", results = { @Result(name = "success", type = "redirect", location = "manage") })
    public String serve() {
        orderService.serveOrder(orderId);
        return SUCCESS;
    }

    @Action(value = "requestRefund", results = { @Result(name = "success", type = "redirect", location = "myOrders") })
    public String requestRefund() {
        orderService.requestRefund(orderId);
        return SUCCESS;
    }

    @Action(value = "confirmRefund", results = { @Result(name = "success", type = "redirect", location = "manage") })
    public String confirmRefund() {
        orderService.confirmRefund(orderId);
        return SUCCESS;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTableNum(Integer tableNum) {
        this.tableNum = tableNum;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
