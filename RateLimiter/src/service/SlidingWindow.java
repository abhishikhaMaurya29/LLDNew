package service;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindow implements RateLimiter {
    private final long windowSizeInMillis;
    private final int maxRequests;

    Deque<Long> requests;

    public SlidingWindow(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requests = new ArrayDeque<>();
    }

    @Override
    public synchronized boolean allowRequests() {
        long now = System.currentTimeMillis();

        while (!requests.isEmpty() && now - requests.peekFirst() >= windowSizeInMillis) {
            requests.pollFirst();
        }

        if (requests.size() < maxRequests) {
            requests.addLast(now);
            return true;
        }

        return false;
    }
}