package model.Order;

import model.MenuItem;

import java.util.List;

public final class OrderDetails {

    private final String orderId;
    private final String userId;
    private final String restaurantId;
    private final List<MenuItem> items;
    private final double totalAmount;

    public OrderDetails(String orderId,
                        String userId,
                        String restaurantId,
                        List<MenuItem> items,
                        double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.items = List.copyOf(items); // defensive copy
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
