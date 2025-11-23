import java.util.PriorityQueue;
import java.util.TreeSet;

public interface SchedulerStrategy {
    int getNextFloor(int currentFloor, Direction direction, TreeSet<Integer> up, TreeSet<Integer> down);
}
