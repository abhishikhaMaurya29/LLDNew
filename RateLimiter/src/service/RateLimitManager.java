package service;

public interface RateLimitManager {
    boolean isRequestAllowed(String user);
}