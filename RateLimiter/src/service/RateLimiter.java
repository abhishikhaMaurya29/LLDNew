package service;

public interface RateLimiter {
    boolean allowRequests();
}
