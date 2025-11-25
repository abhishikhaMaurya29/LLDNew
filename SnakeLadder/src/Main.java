import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Dice dice = new DefaultDice(6);
        List<Snake> snakeList = List.of(new Snake(42, 32), new Snake(98, 20));
        List<Ladder> ladderList = List.of(new Ladder(12, 24));

        List<Player> players1 = List.of(new Player("Tony"), new Player("Park"));
        List<Player> players2 = List.of(new Player("Jungkook"), new Player("Bam"));

        Board board = new Board(100, snakeList, ladderList);

        Game game1 = GameManager.INSTANCE.createGame(board, dice, players1);
        Game game2 = GameManager.INSTANCE.createGame(board, dice, players2);

        game1.start();
        game2.start();

        Thread t1 = new Thread(() -> {
            try {
                runGame(game1);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                runGame(game2);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Winners:");
        System.out.println("Game1: " + game1.getWinner().name);
        System.out.println("Game2: " + game2.getWinner().name);

        GameManager.INSTANCE.shutDown();
    }

    private static void runGame(Game game) throws InterruptedException, ExecutionException {
        while (game.getGameStatus() != GameStatus.FINISHED) {
            try {
                MoveResult r = game.playTurnAsync().get(); // async inside game
                System.out.println(
                        "Game " + game.getGameId() + ": " +
                                r.getPlayer().name + " moved to " + r.getTo()
                );
//                Thread.sleep(200); // slow down for readability (optional)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(
                "Winner of game " + game.getGameId() +
                        " = " + game.getWinner().name
        );
    }
}