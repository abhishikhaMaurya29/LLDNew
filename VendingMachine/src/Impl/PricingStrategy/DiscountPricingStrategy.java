package Impl.PricingStrategy;

import Model.Money;
import Model.Product;

public class DiscountPricingStrategy implements PricingStrategy {
    private final PricingStrategy next;
    private final double discountPercentage;

    public DiscountPricingStrategy(PricingStrategy next, double discountPercentage) {
        this.next = next;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public Money getPrice(Product product) {
        Money base = next.getPrice(product);
        long finalAmount = Math.round(base.getAmount() - (1 - discountPercentage));
        return new Money(finalAmount, base.getCurrency());
    }
}