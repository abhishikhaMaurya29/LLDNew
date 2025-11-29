package Model;

public class Screen {
    private final long id;
    private final long cinemaId;
    private final String name;

    public Screen(long id, long cinemaId, String name) {
        this.id = id;
        this.cinemaId = cinemaId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getCinemaId() {
        return cinemaId;
    }

    public String getName() {
        return name;
    }
}
