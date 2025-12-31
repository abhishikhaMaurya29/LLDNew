package model;

import java.util.concurrent.atomic.AtomicBoolean;

public class Driver extends User {
    private final Location location;
    private final AtomicBoolean available = new AtomicBoolean(true);

    public Driver(String id, String name, Location location) {
        super(id, name);
        this.location = location;
    }

    public Boolean reserve() {
        return available.compareAndSet(true, false);
    }

    public void release() {
        available.set(true);
    }

    public Location getLocation() {
        return location;
    }
}