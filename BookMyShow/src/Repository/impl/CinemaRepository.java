package Repository.impl;

import Model.Cinema;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CinemaRepository {
    private final Map<Long, Cinema> cinemas = new ConcurrentHashMap<>();

    public void save(Cinema cinema) {
        cinemas.put(cinema.getId(), cinema);
    }

    public void remove(long id) {
        if (cinemas.remove(id) == null) {
            throw new IllegalStateException("No Cinema with the cinema id {0} exists." + id);
        }
    }

    public Cinema getMovie(long id) {
        return cinemas.get(id);
    }

    public List<Cinema> getCinemaByCity(String city) {
        return cinemas.values().stream().filter(c -> c.getCity().equals(city))
                .toList();
    }
}