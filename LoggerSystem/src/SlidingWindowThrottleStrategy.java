import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowThrottleStrategy implements LoggerThrottleStrategy {
    Map<String, Deque<Long>> lastWriteTimeStamps = new ConcurrentHashMap<>();

    @Override
    public boolean shouldPrint(String message, long timeStamp, LoggerThrottleRule rule) {
        if (!lastWriteTimeStamps.containsKey(message)) {
            Deque<Long> deque = new ArrayDeque<>();
            deque.addLast(timeStamp);
            lastWriteTimeStamps.put(message, deque);
            return true;
        }

        Deque<Long> deque = lastWriteTimeStamps.get(message);

        while (!deque.isEmpty() && timeStamp - deque.peekFirst() >= rule.getWindowSize()) {
            deque.pollFirst();
        }

        if (deque.isEmpty()) {
            deque.offer(timeStamp);
            return true;
        }

        return false;
    }
}