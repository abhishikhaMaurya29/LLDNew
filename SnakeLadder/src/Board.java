import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Board {
    int size;
    Map<Integer, Integer> jumps = new ConcurrentHashMap<>();

    public Board(int size, List<Snake> snakes, List<Ladder> ladders) {
        this.size = size;

        for (Snake snake : snakes) {
            if (jumps.containsKey(snake.getStart())) {
                throw new IllegalArgumentException("Multiple jumps cannot start at " + snake.getStart());
            }
            jumps.put(snake.getStart(), snake.getEnd());
        }

        for (Ladder ladder : ladders) {
            if (jumps.containsKey(ladder.getStart())) {
                throw new IllegalArgumentException("Multiple jumps cannot start at " + ladder.getStart());
            }
            jumps.put(ladder.getStart(), ladder.getEnd());
        }
    }

    public int getSize() {
        return size;
    }

    public int getNextPosition(int currentPosition) {
        return jumps.getOrDefault(currentPosition, currentPosition);
    }
}