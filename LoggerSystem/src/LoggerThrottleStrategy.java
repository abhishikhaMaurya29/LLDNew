public interface LoggerThrottleStrategy {
    boolean shouldPrint(String message, long timeStamp, LoggerThrottleRule rule);
}
