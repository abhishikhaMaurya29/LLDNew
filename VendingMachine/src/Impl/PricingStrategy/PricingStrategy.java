package Impl.PricingStrategy;

import Model.Money;
import Model.Product;

public interface PricingStrategy {
    Money getPrice(Product product);
}