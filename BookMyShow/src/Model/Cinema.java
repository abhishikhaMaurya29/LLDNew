package Model;

public class Cinema {
    private final long id;
    private final String name;
    private final String city;

    public Cinema(long id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
