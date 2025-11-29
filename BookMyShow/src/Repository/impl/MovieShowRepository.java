package Repository.impl;

import Model.MovieShow;

import java.util.HashMap;
import java.util.Map;

public class MovieShowRepository {
    private final Map<Long, MovieShow> shows = new HashMap<>();

    public void save(MovieShow show) {
        shows.put(show.getId(), show);
    }

    public MovieShow get(long id) {
        return shows.get(id);
    }
}
