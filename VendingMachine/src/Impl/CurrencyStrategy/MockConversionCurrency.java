package Impl.CurrencyStrategy;

import Model.Money;

import java.util.Currency;
import java.util.Map;

public class MockConversionCurrency implements CurrencyConversion {
    private final Map<String, Double> rates = Map.of(
            "USD_IND", 12.5,
            "IND_USD", 13.5
    );

    @Override
    public Money currencyConversion(Money sourceMoney, Currency targetCurrency) {
        String currencyString = sourceMoney.getCurrency().getCurrencyCode() + "_" + targetCurrency.getCurrencyCode();
        long finalAmount = Math.round(rates.get(currencyString) * sourceMoney.getAmount());
        return new Money(finalAmount, targetCurrency);
    }
}