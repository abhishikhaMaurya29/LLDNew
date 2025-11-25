public class MoveResult {
    Player player;
    int from;
    int to;
    boolean snakeOrLadder;
    boolean won;

    public MoveResult(Player player, int from, int to, boolean snakeOrLadder, boolean won) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.snakeOrLadder = snakeOrLadder;
        this.won = won;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean isSnakeOrLadder() {
        return snakeOrLadder;
    }

    public void setSnakeOrLadder(boolean snakeOrLadder) {
        this.snakeOrLadder = snakeOrLadder;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}
