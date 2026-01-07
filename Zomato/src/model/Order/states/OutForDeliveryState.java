package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderContext;
import model.Order.OrderStatus;

public class OutForDeliveryState extends AbstractOrderState {
    public OrderStatus status() {
        return OrderStatus.OUT_FOR_DELIVERY;
    }

    public void deliver(OrderContext ctx) {
        ctx.transitionTo(new DeliveredState());
    }
}