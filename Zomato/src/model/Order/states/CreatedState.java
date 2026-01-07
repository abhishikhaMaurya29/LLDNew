package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderContext;
import model.Order.OrderStatus;

public class CreatedState extends AbstractOrderState {
    public OrderStatus status() {
        return OrderStatus.CREATED;
    }

    public void pay(OrderContext ctx) {
        ctx.transitionTo(new PaidState());
    }

    public void cancel(OrderContext ctx) {
        ctx.transitionTo(new CancelledState());
    }
}