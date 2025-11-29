package Model;

public class Movie {
    private final long id;
    private final String title;
    private final int duration;
    private final int ratings;

    public Movie(long id, String title, int duration, int ratings) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.ratings = ratings;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getRatings() {
        return ratings;
    }
}
