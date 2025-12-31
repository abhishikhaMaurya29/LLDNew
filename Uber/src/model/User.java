package model;

public abstract class User {
    private final String id;
    protected final String name;

    protected User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
}