package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderContext;
import model.Order.OrderStatus;

public class PaidState extends AbstractOrderState {
    public OrderStatus status() {
        return OrderStatus.PAID;
    }

    public void accept(OrderContext ctx) {
        ctx.transitionTo(new AcceptedState());
    }
}
