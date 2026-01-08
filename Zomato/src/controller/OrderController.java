package controller;

import model.MenuItem;
import model.Order.OrderContext;
import model.Order.states.OrderSaga;
import model.Restaurant;
import model.User;
import service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderSaga orderSaga;
    private final OrderService orderService;

    public OrderController(OrderSaga orderSaga, OrderService orderService) {
        this.orderSaga = orderSaga;
        this.orderService = orderService;
    }

    public void placeOrder(User user, Restaurant restaurant, List<MenuItem> items) {
        OrderContext orderContext = orderService.placeOrder(user, restaurant, items);
        orderSaga.execute(orderContext);
    }
}