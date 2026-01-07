package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderContext;
import model.Order.OrderStatus;

public class PreparingState extends AbstractOrderState {
    public OrderStatus status() {
        return OrderStatus.PREPARING;
    }

    public void dispatch(OrderContext ctx) {
        ctx.transitionTo(new OutForDeliveryState());
    }
}