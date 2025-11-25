import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum GameManager {
    INSTANCE;

    private final Map<String, Game> games = new ConcurrentHashMap<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(50);

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void shutDown() {
        executorService.shutdown();
    }

    public Game createGame(Board board, Dice dice, List<Player> players) {
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId, players, dice, board);
        games.put(gameId, game);
        return game;
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public void removeGame(String gameId) {
        games.remove(gameId);
    }
}
