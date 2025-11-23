public class LoggerThrottleRule {
    private final int windowSize;

    public LoggerThrottleRule(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getWindowSize() {
        return this.windowSize;
    }
}
