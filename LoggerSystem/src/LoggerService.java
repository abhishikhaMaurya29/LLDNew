public class LoggerService {
    private final LoggerThrottleStrategy throttleStrategy;
    private final LoggerThrottleRule rule;

    public LoggerService(LoggerThrottleStrategy throttleStrategy, LoggerThrottleRule rule) {
        this.throttleStrategy = throttleStrategy;
        this.rule = rule;
    }

    public void log(int timestamp, String message) {
        boolean allowed = throttleStrategy.shouldPrint(message, timestamp, rule);

        if (allowed) {
            System.out.println("PRINT: " + message);
        } else {
            System.out.println("SUPPRESSED: " + message);
        }
    }
}
