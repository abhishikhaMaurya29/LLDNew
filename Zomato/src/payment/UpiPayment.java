package payment;

import model.PaymentRequest;

public class UpiPayment implements PaymentStrategy {
    @Override
    public void pay(PaymentRequest paymentRequest) {
        System.out.println("Upi payment done");
    }
}