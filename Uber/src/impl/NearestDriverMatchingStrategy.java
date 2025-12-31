package impl;

import model.Driver;
import model.Location;
import strategy.DriverMatchingStrategy;

import java.util.List;

public class NearestDriverMatchingStrategy implements DriverMatchingStrategy {
    @Override
    public Driver findDriver(Location location, Iterable<Driver> drivers) {
        for (Driver driver : drivers) {
            if (driver.reserve()) {
                return driver;
            }
        }
        return null;
    }
}