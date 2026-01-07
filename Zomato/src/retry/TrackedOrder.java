package retry;

import model.Order.OrderContext;
import model.Order.OrderStatus;

public class TrackedOrder {
    private final OrderContext orderContext;
    private final OrderStatus expectedState;
    private final Runnable onTimeout;

    public TrackedOrder(OrderContext orderContext, OrderStatus expectedState, Runnable onTimeout) {
        this.orderContext = orderContext;
        this.expectedState = expectedState;
        this.onTimeout = onTimeout;
    }

    public boolean isExpired() {
        return orderContext.status() == expectedState;
    }
}
