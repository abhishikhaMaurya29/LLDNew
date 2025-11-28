package Impl.PaymentStrategy;

import Model.Money;

import java.util.Currency;

public class CashPaymentStrategy implements PaymentStrategy {
    private final Currency currency;
    private long insertedAmount;

    public CashPaymentStrategy(Currency currency) {
        this.currency = currency;
    }

    public void insertedCash(Money money) {
        validateCurrency(money);
        insertedAmount += money.getAmount();
    }

    @Override
    public boolean pay(Money amount) {
        validateCurrency(amount);

        long price = amount.getAmount();

        if (insertedAmount < price) {
            System.out.println("Not enough cash inserted. Needed: " + amount);
            return false;
        }

        insertedAmount -= price;
        return true;
    }

    @Override
    public Money refund() {
        if (insertedAmount <= 0) {
            throw new IllegalStateException("There is no money to refund");
        }
        Money refunded = new Money(insertedAmount, this.currency);
        insertedAmount = 0;
        return refunded;
    }

    private void validateCurrency(Money money) {
        if (!money.getCurrency().equals(this.currency)) {
            throw new IllegalArgumentException("Currency mismatch!");
        }
    }
}