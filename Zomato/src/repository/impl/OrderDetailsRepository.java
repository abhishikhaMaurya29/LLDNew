package repository.impl;

import model.Order.OrderDetails;

public interface OrderDetailsRepository {
    void save(OrderDetails orderDetails);

    OrderDetails findByOrderId(String orderId);
}