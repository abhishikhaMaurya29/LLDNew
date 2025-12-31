package repository;

import model.Driver;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverRepository {
    public Map<String, Driver> drivers = new ConcurrentHashMap<>();

    public void save(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    public Collection<Driver> getAllDriver() {
        return drivers.values();
    }
}