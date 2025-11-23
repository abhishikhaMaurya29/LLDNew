public class Main {
    public static void main(String[] args) {
        LoggerService logger = new LoggerService(new SlidingWindowThrottleStrategy(), new LoggerThrottleRule(5));

        logger.log(1, "foo");   // prints
        logger.log(5, "foo");   // suppressed
        logger.log(11, "foo");  // prints
        logger.log(12, "bar");  // prints
    }
}