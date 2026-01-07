package service;

import model.*;
import model.Order.OrderContext;
import model.Order.OrderDetails;
import model.Order.OrderSnapshot;
import repository.impl.OrderDetailsRepository;
import repository.impl.OrderRepository;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final PaymentService paymentService;
    private final DeliveryService deliveryService;

    public OrderService(OrderRepository orderRepository, PaymentService paymentService,
                        DeliveryService deliveryService, OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public OrderContext placeOrder(User user, Restaurant restaurant, List<MenuItem> items) {

        if (!restaurant.isOpen()) {
            throw new IllegalStateException("Restaurant is closed");
        }

        OrderContext orderContext = new OrderContext(UUID.randomUUID().toString());

        OrderDetails orderDetails = new OrderDetails(orderContext.getOrderId(), user.getUserId(),
                restaurant.getRestaurantId(), items, calculateTotal(items));

        orderRepository.create(orderContext);
        orderDetailsRepository.save(orderDetails);

        return orderContext;
    }

    private double calculateTotal(List<MenuItem> items) {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }

        return items.stream().mapToDouble(MenuItem::getAmt).sum();
    }

    public void payAndConfirm(PaymentRequest request) {
        OrderContext order = orderRepository.findById(request.getOrderId());

        OrderSnapshot snapshot = order.snapshot();

        paymentService.processPayment(request);

        order.pay(snapshot.getVersion());

        orderRepository.update(order, snapshot.getVersion());

        deliveryService.assignDeliveryPartner(order);
    }
}