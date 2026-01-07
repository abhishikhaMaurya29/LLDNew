package model;

import java.util.concurrent.atomic.AtomicBoolean;

public class MenuItem {
    private final String itemId;
    private final String name;
    private final double amt;
    private AtomicBoolean isAvailable;

    public MenuItem(String itemId, String name, double amt, AtomicBoolean isAvailable) {
        this.itemId = itemId;
        this.name = name;
        this.amt = amt;
        this.isAvailable = isAvailable;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getAmt() {
        return amt;
    }

    public AtomicBoolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(AtomicBoolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}