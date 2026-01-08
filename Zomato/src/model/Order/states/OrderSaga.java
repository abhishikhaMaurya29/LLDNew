package model.Order.states;

import model.Order.OrderContext;
import model.Order.OrderSnapshot;
import model.Order.OrderStatus;
import model.PaymentRequest;
import payment.PaymentType;
import retry.TimedStateTracker;
import service.DeliveryService;
import service.PaymentService;

public class OrderSaga {
    private final PaymentService paymentService;
    private final DeliveryService deliveryService;
    private final TimedStateTracker timedStateTracker;

    public OrderSaga(PaymentService paymentService, DeliveryService deliveryService, TimedStateTracker timedStateTracker) {
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
        this.timedStateTracker = timedStateTracker;
    }

    public void execute(OrderContext orderContext) {
        try {
            OrderSnapshot s1 = orderContext.snapshot();
            PaymentRequest paymentRequest = new PaymentRequest(orderContext.getOrderId(),
                    "idem-key", PaymentType.CARD);
            paymentService.processPayment(paymentRequest);
            orderContext.pay(s1.getVersion());

            timedStateTracker.track(orderContext, OrderStatus.PAID, 2 * 60 * 1000, () -> {
                paymentService.refund(orderContext.getOrderId());
            });

            // clear previous timeout on success
            timedStateTracker.clear(orderContext.getOrderId());

            OrderSnapshot s2 = orderContext.snapshot();
            orderContext.accept(s2.getVersion());

            timedStateTracker.track(orderContext, OrderStatus.ACCEPTED, 2 * 60 * 1000, () -> {
                paymentService.refund(orderContext.getOrderId());
            });

            timedStateTracker.clear(orderContext.getOrderId());

            OrderSnapshot s3 = orderContext.snapshot();
            deliveryService.assignDeliveryPartner(orderContext);
            orderContext.dispatch(s3.getVersion());

            timedStateTracker.track(orderContext, OrderStatus.OUT_FOR_DELIVERY, 2 * 60 * 1000, () -> {
                deliveryService.release(orderContext.getOrderId());
            });
        } catch (Exception ex) {
            paymentService.refund(orderContext.getOrderId());
            throw ex;
        }
    }

    private void compensate(OrderContext orderContext) {
        OrderStatus status = orderContext.status();

        if (status == OrderStatus.PAID || status == OrderStatus.ACCEPTED) {
            paymentService.refund(orderContext.getOrderId());
        }

        if (status == OrderStatus.OUT_FOR_DELIVERY) {
            deliveryService.release(orderContext.getOrderId());
        }
    }
}