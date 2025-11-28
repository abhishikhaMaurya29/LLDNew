package Impl.PricingStrategy;

import Model.Money;
import Model.Product;

public class DefaultPricingStrategy implements PricingStrategy {

    @Override
    public Money getPrice(Product product) {
        return product.getMoney();
    }
}
