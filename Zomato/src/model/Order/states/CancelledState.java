package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderStatus;

public class CancelledState extends AbstractOrderState {
    public OrderStatus orderStatus() {
        return OrderStatus.CANCELLED;
    }
}