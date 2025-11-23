package service;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucket implements RateLimiter {
    private double tokens;
    private final double capacity;
    private final double refillRateInMillis;
    private long lastRefillTime;
    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucket(double capacity, double refillRatePerSec) {
        this.capacity = capacity;
        this.refillRateInMillis = refillRatePerSec / 1000;
        this.lastRefillTime = System.currentTimeMillis();
        this.tokens = 0;
    }

    @Override
    public synchronized boolean allowRequests() {
        lock.lock();

        try {
            long now = System.currentTimeMillis();

            double tokenToAdd = (now - lastRefillTime) * refillRateInMillis;
            tokens = Math.min(capacity, tokenToAdd + tokens);

            lastRefillTime = now;

            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}

//Recommended Choice: ReentrantLock
//The ReentrantLock is recommended because your implementation relies on a specific pattern that works best with explicit
//locks: the try-finally pattern.
//
//Why ReentrantLock is suitable:
//Guaranteed Release: The try-finally block structure ensures that the lock.unlock() method is called even if an
//unexpected exception occurs within the critical section (though none are likely in this specific code).
//This is the standard, reliable pattern for explicit locks.
//
//Clarity of Scope: The explicit lock() and unlock() calls clearly define the exact beginning and end of the critical
//section where shared state (tokens, lastRefillTime) is modified.
//
//Potential Performance: In highly concurrent environments (many threads repeatedly hitting the allowRequests method),
//ReentrantLock might offer better performance than intrinsic synchronized locks due to advanced features in the JVM's
//implementation of explicit locks when threads are heavily contending for access.