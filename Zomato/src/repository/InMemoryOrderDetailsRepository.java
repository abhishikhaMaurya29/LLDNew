package repository;

import model.Order.OrderDetails;
import repository.impl.OrderDetailsRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderDetailsRepository implements OrderDetailsRepository {

    private final Map<String, OrderDetails> store = new ConcurrentHashMap<>();

    @Override
    public void save(OrderDetails orderDetails) {
        OrderDetails existing = store.putIfAbsent(
                orderDetails.getOrderId(), orderDetails
        );

        if (existing != null) {
            throw new IllegalStateException("OrderDetails already exists for orderId : " + orderDetails.getOrderId());
        }
    }

    @Override
    public OrderDetails findByOrderId(String orderId) {
        return store.get(orderId);
    }
}