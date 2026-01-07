package model;

public class Restaurant {
    private final String restaurantId;
    private String name;
    private String location;
    private volatile boolean isOpen;
    private final Menu menu;

    public Restaurant(String restaurantId, String name, String location, boolean isOpen, Menu menu) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;
        this.isOpen = isOpen;
        this.menu = menu;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Menu getMenu() {
        return menu;
    }
}