package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderContext;
import model.Order.OrderStatus;

public class AcceptedState extends AbstractOrderState {
    public OrderStatus status() {
        return OrderStatus.ACCEPTED;
    }

    @Override
    public void prepare(OrderContext ctx) {
        ctx.transitionTo(new PreparingState());
    }
}