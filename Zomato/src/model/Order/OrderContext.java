package model.Order;

import model.Order.states.CreatedState;

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class OrderContext {
    private final String orderId;
    private final AtomicInteger version = new AtomicInteger(0);
    private OrderState orderState;

    public OrderContext(String orderId) {
        this.orderId = orderId;
        this.orderState = new CreatedState();
    }

    public synchronized void transition(Consumer<OrderState> action,
                                        int expectedVersion) {
        if (version.get() != expectedVersion) {
            throw new ConcurrentModificationException("Stale update.");
        }

        OrderStatus before = orderState.status();
        action.accept(orderState);

        if (orderState.status() == before) {
            throw new IllegalStateException("State did not changed");
        }

        version.incrementAndGet();
    }

    public void transitionTo(OrderState orderState) {
        this.orderState = orderState;
    }

    public OrderSnapshot snapshot() {
        return new OrderSnapshot(orderId, orderState.status(), version.get());
    }

    public void pay(int v) {
        transition(s -> s.pay(this), v);
    }

    public void accept(int v) {
        transition(s -> s.accept(this), v);
    }

    public void prepare(int v) {
        transition(s -> s.prepare(this), v);
    }

    public void dispatch(int v) {
        transition(s -> s.dispatch(this), v);
    }

    public void deliver(int v) {
        transition(s -> s.deliver(this), v);
    }

    public OrderStatus status() {
        return orderState.status();
    }

    public String getOrderId() {
        return orderId;
    }
}