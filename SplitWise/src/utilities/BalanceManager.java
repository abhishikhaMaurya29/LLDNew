package utilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class BalanceManager {
    private final static Map<String, ReentrantLock> lock = new ConcurrentHashMap<>();

    public static ReentrantLock getLock(String key) {
        return lock.computeIfAbsent(key, _ -> new ReentrantLock());
    }
}
