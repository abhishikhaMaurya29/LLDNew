package repository.impl;

import model.Order.OrderContext;

public interface OrderRepository {
    void create(OrderContext order);

    void update(OrderContext order, int version);

    OrderContext findById(String id);
}