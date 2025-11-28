package Impl.PricingStrategy;

import Model.Money;
import Model.Product;

public class MembershipPricingStrategy implements PricingStrategy {
    private final PricingStrategy next;
    private final Money flat;

    public MembershipPricingStrategy(PricingStrategy next, Money flat) {
        this.next = next;
        this.flat = flat;
    }

    @Override
    public Money getPrice(Product product) {
        Money base = next.getPrice(product);

        if (!flat.getCurrency().equals(base.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatched");
        }

        long finalAmount = Math.max(0, base.getAmount() - flat.getAmount());
        return new Money(finalAmount, base.getCurrency());
    }
}
