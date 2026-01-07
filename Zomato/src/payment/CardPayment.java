package payment;

import model.PaymentRequest;

public class CardPayment implements PaymentStrategy {
    @Override
    public void pay(PaymentRequest paymentRequest) {
        System.out.println("Card payment successful");
    }
}