package retry;

import java.util.function.Supplier;

public class RetryPolicy {
    private final int maxAttempts;
    private final long initialDelayMs;
    private final double multiplier;

    public RetryPolicy(int maxAttempts, long initialDelayMs, double multiplier) {
        this.maxAttempts = maxAttempts;
        this.initialDelayMs = initialDelayMs;
        this.multiplier = multiplier;
    }

    public void execute(Runnable action) {
        long delay = initialDelayMs;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                action.run();
                return;
            } catch (Exception e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
                sleep(delay);
                delay = (long) (delay * multiplier);
            }
        }
    }

    public <T> T executeWithResult(Supplier<T> supplier) {
        long delay = initialDelayMs;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return supplier.get();
            } catch (Exception e) {
                if (attempt == maxAttempts) {
                    throw e;
                }
                sleep(delay);
                delay = (long) (delay * multiplier);
            }
        }
        throw new IllegalStateException("Unreachable");
    }

    public void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}