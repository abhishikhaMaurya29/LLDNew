package retry;

import model.Order.OrderContext;
import model.Order.OrderStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedStateTracker {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, TrackedOrder> tracked = new ConcurrentHashMap<>();

    public void track(OrderContext order, OrderStatus expectedState, long timeoutMs, Runnable onTimeOut) {
        TrackedOrder trackedOrder = new TrackedOrder(order, expectedState, onTimeOut);
        tracked.put(order.getOrderId(), trackedOrder);

        scheduler.schedule(() -> {
            TrackedOrder current = tracked.get(order.getOrderId());
            if (current != null && current.shouldFire()) {
                try {
                    onTimeOut.run();
                } finally {
                    tracked.remove(order.getOrderId());
                }
            }
        }, timeoutMs, TimeUnit.MILLISECONDS);
    }

    public void clear(String orderId) {
        tracked.remove(orderId);
    }

    static class TrackedOrder {
        final OrderContext order;
        final Runnable onTimeout;
        final OrderStatus expectedStatus;

        public TrackedOrder(OrderContext order, OrderStatus expectedStatus, Runnable onTimeout) {
            this.order = order;
            this.onTimeout = onTimeout;
            this.expectedStatus = expectedStatus;
        }

        boolean shouldFire() {
            return order.status() == expectedStatus;
        }
    }
}