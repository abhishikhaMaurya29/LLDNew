package Impl.PaymentStrategy;

import Model.Money;

public interface PaymentStrategy {
    boolean pay(Money amount);

    Money refund();
}