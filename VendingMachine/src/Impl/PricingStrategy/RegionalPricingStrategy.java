package Impl.PricingStrategy;

import Model.Money;
import Model.Product;
import Model.Region;

import java.util.Map;

public class RegionalPricingStrategy implements PricingStrategy {
    private final PricingStrategy next;
    private final Map<Region, Money> regionOverrides;
    private final Region region;

    public RegionalPricingStrategy(PricingStrategy next, Map<Region, Money> regionOverrides, Region region) {
        this.next = next;
        this.regionOverrides = regionOverrides;
        this.region = region;
    }

    @Override
    public Money getPrice(Product product) {
        if (regionOverrides.containsKey(this.region)) {
            return regionOverrides.get(this.region);
        }

        return next.getPrice(product);
    }
}