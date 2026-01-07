package model;

public class User {
    private final String userId;
    private String name;
    private String location;

    public User(String userId, String name, String location) {
        this.userId = userId;
        this.name = name;
        this.location = location;
    }

    public String getUserId() {
        return userId;
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
}