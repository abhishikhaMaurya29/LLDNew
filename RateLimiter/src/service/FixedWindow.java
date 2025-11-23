package service;

public class FixedWindow implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private long windowStartTime;
    private int currentRequests;

    public FixedWindow(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.windowStartTime = System.currentTimeMillis();
        this.currentRequests = 0;
    }

    @Override
    public synchronized boolean allowRequests() {
        long now = System.currentTimeMillis();

        if (now - windowStartTime >= windowSizeInMillis) {
            currentRequests = 0;
            windowStartTime = now;
        }

        if (currentRequests < maxRequests) {
            currentRequests++;
            return true;
        }
        return false;
    }
}