package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PartnerLockRegistry {
    private static final ConcurrentHashMap<String, ReentrantLock> locks =
            new ConcurrentHashMap<>();

    public static ReentrantLock getLock(String partnerId) {
        return locks.computeIfAbsent(partnerId, id -> new ReentrantLock());
    }
}