package Model;

import java.util.Currency;

public class Money {
    private final long amountMinor;
    private final Currency currency;

    public Money(long amountMinor, Currency currency) {
        this.amountMinor = amountMinor;
        this.currency = currency;
    }

    public long getAmount() {
        return amountMinor;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString(){
        return (amountMinor / 100) + " - " + currency.getCurrencyCode();
    }
}
