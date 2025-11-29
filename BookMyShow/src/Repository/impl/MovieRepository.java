package Repository.impl;

import Model.Movie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MovieRepository {
    private final Map<Long, Movie> movies = new ConcurrentHashMap<>();

    public void save(Movie movie) {
        movies.put(movie.getId(), movie);
    }

    public void remove(long id) {
        if (movies.remove(id) == null) {
            throw new IllegalStateException("No movie with the booking id {0} exists." + id);
        }
    }

    public Movie getMovie(long id) {
        return movies.get(id);
    }
}