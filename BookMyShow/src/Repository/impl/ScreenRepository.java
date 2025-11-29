package Repository.impl;

import Model.Screen;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScreenRepository {
    private final Map<Long, Screen> screens = new ConcurrentHashMap<>();

    public void save(Screen screen) {
        screens.put(screen.getId(), screen);
    }

    public void remove(long id) {
        if (screens.remove(id) == null) {
            throw new IllegalStateException("No Cinema with the cinema id {0} exists." + id);
        }
    }

    public Screen getMovie(long id) {
        return screens.get(id);
    }

    public List<Screen> getScreensByCinemaId(long cinemaId) {
        return screens.values().stream().filter(c -> c.getCinemaId() == cinemaId)
                .toList();
    }
}