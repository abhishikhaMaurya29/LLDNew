package controller;

import model.MenuItem;
import model.Order.OrderContext;
import model.Restaurant;
import model.User;
import service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderContext placeOrder(User user, Restaurant restaurant, List<MenuItem> items) {
        if (!restaurant.isOpen()) {
            throw new IllegalStateException("Restaurant closed");
        }
        return orderService.placeOrder(user, restaurant, items);
    }
}