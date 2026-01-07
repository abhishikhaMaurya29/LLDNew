package model.Order.states;

import model.Order.AbstractOrderState;
import model.Order.OrderStatus;

public class DeliveredState extends AbstractOrderState {
    public OrderStatus status() {
        return OrderStatus.DELIVERED;
    }
}