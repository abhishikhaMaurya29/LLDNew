package payment;

import model.PaymentRequest;

public interface PaymentStrategy {
    void pay(PaymentRequest paymentRequest);
}