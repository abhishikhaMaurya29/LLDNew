package model.Order;

public class OrderSnapshot {
    private final String orderId;
    private final OrderStatus orderStatus;
    private final int version;

    public OrderSnapshot(String orderId, OrderStatus orderStatus, int version) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}