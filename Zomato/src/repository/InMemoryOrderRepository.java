package repository;

import model.Order.OrderContext;
import repository.impl.OrderRepository;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, OrderContext> orders = new ConcurrentHashMap<>();

    @Override
    public void create(OrderContext order) {
        orders.put(order.getOrderId(), order);
    }

    @Override
    public void update(OrderContext order, int expectedVersion) {
        OrderContext existing = orders.get(order.getOrderId());

        if (existing == null) {
            throw new IllegalStateException("Order not found");
        }

        if (existing.snapshot().getVersion() != expectedVersion) {
            throw new ConcurrentModificationException("Order modified concurrently");
        }

        orders.put(order.getOrderId(), order);
    }

    @Override
    public OrderContext findById(String id) {
        return orders.get(id);
    }
}