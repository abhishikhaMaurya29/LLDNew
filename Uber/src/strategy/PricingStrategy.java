package strategy;

import model.Location;

public interface PricingStrategy {
    double calculateFare(Location from, Location to);
}