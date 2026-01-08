package model;

import java.util.concurrent.atomic.AtomicBoolean;

public class DeliveryPartner {
    private final String id;
    private final String name;
    private String currentLocation;
    private AtomicBoolean isAvailable;

    public DeliveryPartner(String id, String name, String currentLocation) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.isAvailable = new AtomicBoolean(true);
    }

    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable.get();
    }

    public boolean markBusy() {
        return isAvailable.compareAndSet(true, false);
    }
}