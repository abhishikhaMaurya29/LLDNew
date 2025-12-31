package impl;

import model.Location;
import strategy.PricingStrategy;

public class DefaultPricingStrategy implements PricingStrategy {
    @Override
    public double calculateFare(Location from, Location to) {
        return 100.0;
    }
}