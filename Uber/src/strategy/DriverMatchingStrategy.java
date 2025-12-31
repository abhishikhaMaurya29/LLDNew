package strategy;

import model.Driver;
import model.Location;

import java.util.List;

public interface DriverMatchingStrategy {
    Driver findDriver(Location location, Iterable<Driver> drivers);
}