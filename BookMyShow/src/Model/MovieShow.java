package Model;

import java.time.LocalDateTime;
import java.util.Locale;

public class MovieShow {
    private final long id;
    private final long movieId;
    private final long screenId;
    private final LocalDateTime startTime;

    public MovieShow(long id, long movieId, long screenId, LocalDateTime startTime) {
        this.id = id;
        this.movieId = movieId;
        this.screenId = screenId;
        this.startTime = startTime;
    }

    public long getId() {
        return id;
    }

    public long getMovieId() {
        return movieId;
    }

    public long getScreenId() {
        return screenId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
