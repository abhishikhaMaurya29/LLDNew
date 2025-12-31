import java.util.TreeSet;

public class ScanSchedulerStrategy implements SchedulerStrategy {

    @Override
    public int getNextFloor(int currentFloor, Direction direction, TreeSet<Integer> up, TreeSet<Integer> down) {
        if (direction.equals(Direction.UP)) {
            if (!up.isEmpty()) {
                return up.first();
            }
            if (!down.isEmpty()) {
                return down.first();
            }
        }

        if (direction.equals(Direction.DOWN)) {
            if (!down.isEmpty()) {
                return down.first();
            }
            if (!up.isEmpty()) {
                return up.first();
            }
        }

        int nearest = -1;
        int nextFloor = Integer.MAX_VALUE;

        for (int f : up) {
            int diff = Math.abs(f - currentFloor);
            if (diff < nextFloor) {
                nextFloor = diff;
                nearest = f;
            }
        }

        for (int f : down) {
            int diff = Math.abs(f - currentFloor);
            if (diff < nextFloor) {
                nextFloor = diff;
                nearest = f;
            }
        }

        return nearest;
    }
}
