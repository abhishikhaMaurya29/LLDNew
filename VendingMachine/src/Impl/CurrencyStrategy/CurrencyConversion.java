package Impl.CurrencyStrategy;

import Model.Money;

import java.util.Currency;

public interface CurrencyConversion {
    Money currencyConversion(Money sourceMoney, Currency targetCurrency);
}
