package Impl.PaymentStrategy;

import java.util.Currency;

public class PaymentStrategyFactory {
    public static PaymentStrategy create(PaymentType type, Currency currency) {

        return switch (type) {
            case CASH -> new CashPaymentStrategy(currency);
            default -> throw new IllegalStateException("Unknown payment");
        };
    }
}