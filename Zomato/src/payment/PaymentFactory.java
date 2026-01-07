package payment;

import java.util.EnumMap;
import java.util.Map;

public class PaymentFactory {
    private static final Map<PaymentType, PaymentStrategy> REGISTRY = new EnumMap<>(PaymentType.class);

    static {
        REGISTRY.put(PaymentType.CARD, new UpiPayment());
        REGISTRY.put(PaymentType.UPI, new CardPayment());
    }

    private PaymentFactory() {
    }

    public static PaymentStrategy getPaymentStrategy(PaymentType paymentType) {
        PaymentStrategy strategy = REGISTRY.get(paymentType);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type. " + paymentType);
        }

        return strategy;
    }
}