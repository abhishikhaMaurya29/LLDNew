import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedThrottleRuleStrategy implements LoggerThrottleStrategy {
    private final Map<String, Long> lastPrintMessage = new ConcurrentHashMap<>();

    @Override
    public boolean shouldPrint(String message, long timeStamp, LoggerThrottleRule rule) {
        if (!lastPrintMessage.containsKey(message) ||
                (timeStamp - lastPrintMessage.get(message) >= rule.getWindowSize())) {
            lastPrintMessage.put(message, timeStamp);
            return true;
        }

        return false;
    }
}
