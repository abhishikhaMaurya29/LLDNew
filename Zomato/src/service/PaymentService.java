package service;

import model.PaymentRequest;
import payment.PaymentFactory;
import payment.PaymentStrategy;
import repository.InPaymentRepository;
import retry.RetryConfigs;

public class PaymentService {
    private final InPaymentRepository repository;

    public PaymentService(InPaymentRepository repository) {
        this.repository = repository;
    }

    public void processPayment(PaymentRequest paymentRequest) {
        RetryConfigs.PAYMENT_RETRY.execute(() -> {
            if (repository.isProcessed(paymentRequest.getIdempotencyKey())) {
                return;
            }

            PaymentStrategy paymentStrategy = PaymentFactory.getPaymentStrategy(paymentRequest.getPaymentType());
            paymentStrategy.pay(paymentRequest);
            repository.markProcessed(paymentRequest.getIdempotencyKey());
        });
    }

    public void refund(String orderId) {
        //
    }
}