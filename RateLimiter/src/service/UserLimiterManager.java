package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserLimiterManager implements RateLimitManager {
    private final Map<String, RateLimiter> userBucket = new ConcurrentHashMap<>();
    private final double capacity;
    private final double refillRate;

    public UserLimiterManager(double capacity, double refillRate) {

        this.capacity = capacity;
        this.refillRate = refillRate;
    }

    @Override
    public boolean isRequestAllowed(String user) {
        userBucket.putIfAbsent(user, new TokenBucket(capacity, refillRate));
        return userBucket.get(user).allowRequests();
    }
}