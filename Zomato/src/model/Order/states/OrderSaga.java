package model.Order.states;

import model.DeliveryPartner;
import model.Order.OrderContext;
import model.Order.OrderSnapshot;
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

    void execute(OrderContext orderContext, DeliveryPartner deliveryPartner) {
        try {
            OrderSnapshot s1 = orderContext.snapshot();
            PaymentRequest paymentRequest = new PaymentRequest(orderContext.getOrderId(), "idem-key", PaymentType.CARD);
            paymentService.processPayment(paymentRequest);
            orderContext.pay(s1.getVersion());

            timedStateTracker.track(orderContext, 2 * 60 * 1000, () -> {
                paymentService.refund(orderContext.getOrderId());
            });

            OrderSnapshot s2 = orderContext.snapshot();
            orderContext.accept(s2.getVersion());

            timedStateTracker.track(orderContext, 2 * 60 * 1000, () -> {
                paymentService.refund(orderContext.getOrderId());
            });

            OrderSnapshot s3 = orderContext.snapshot();
            deliveryService.assignDeliveryPartner(orderContext);
            orderContext.dispatch(s3.getVersion());

            timedStateTracker.track(orderContext, 2 * 60 * 1000, () -> {
                deliveryService.release(orderContext.getOrderId());
            });
        } catch (Exception ex) {
            paymentService.refund(orderContext.getOrderId());
            throw ex;
        }
    }
}