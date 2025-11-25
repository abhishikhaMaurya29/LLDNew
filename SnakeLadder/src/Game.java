import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class Game {
    private final String gameId;
    private final Map<Integer, Player> players;
    private final Dice dice;
    private final Board board;
    private int currentIdx;
    private volatile GameStatus gameStatus = GameStatus.NOT_STARTED;
    private Player winner;
    private boolean isRunning = false;

    private final Queue<Runnable> taskQueue = new ArrayDeque<>();

    public Game(String gameId, List<Player> players, Dice dice, Board board) {
        this.gameId = gameId;
        this.players = new ConcurrentHashMap<>();
        int i = 0;
        for (Player player : players) {
            this.players.put(i++, player);
        }

        this.dice = dice;
        this.board = board;
        this.currentIdx = 0;
    }

    public synchronized void start() {
        if (gameStatus != GameStatus.NOT_STARTED) {
            throw new IllegalStateException("Game already started or finished");
        }
        gameStatus = GameStatus.IN_PROGRESS;
    }

    public Future<MoveResult> playTurnAsync() {
        CompletableFuture<MoveResult> future = new CompletableFuture<>();

        synchronized (taskQueue) {
            taskQueue.add(() -> {
                try {
                    if (!GameStatus.IN_PROGRESS.equals(gameStatus)) {
                        future.completeExceptionally(new IllegalStateException("Game is not started"));
                        return;
                    }

                    MoveResult result = turn();
                    future.complete(result);
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            if (!isRunning) {
                isRunning = true;
                scheduleNext();
            }
        }

        return future;
    }

    private void scheduleNext() {
        GameManager.INSTANCE.getExecutorService().submit(() -> {
            Runnable task;
            synchronized (taskQueue) {
                task = taskQueue.poll();
                if (task == null) {
                    isRunning = false;
                    return;
                }
            }

            task.run();
            scheduleNext();
        });
    }

    private synchronized MoveResult turn() {
        if (!GameStatus.IN_PROGRESS.equals(gameStatus)) {
            throw new IllegalArgumentException();
        }

        Player currentPlayer = players.get(currentIdx);
        int tempPosition = currentPlayer.currentPosition + dice.roll();

        if (tempPosition > board.size) {
            System.out.println("Invalid number of jumps : " + tempPosition + ", moving to the next player");
            advancedTurn();
            return new MoveResult(currentPlayer, currentPlayer.currentPosition, currentPlayer.currentPosition, false, false);
        }

        int finalPosition = board.getNextPosition(tempPosition);

        currentPlayer.setCurrentPosition(finalPosition);

        boolean won = finalPosition == board.size;

        if (won) {
            gameStatus = GameStatus.FINISHED;
            System.out.println(currentPlayer);
            winner = currentPlayer;
        }


        boolean snakeOrLadder = tempPosition != finalPosition;

        return new MoveResult(currentPlayer, currentPlayer.currentPosition, finalPosition, snakeOrLadder, won);
    }

    private void advancedTurn() {
        currentIdx = (currentIdx + 1) % players.size();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public String getGameId() {
        return gameId;
    }
}