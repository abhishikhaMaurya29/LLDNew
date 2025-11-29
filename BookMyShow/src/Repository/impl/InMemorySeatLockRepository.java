package Repository.impl;

import Repository.SeatLockRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySeatLockRepository implements SeatLockRepository {
    private final Map<Long, Map<Long, LockInfo>> lockMap = new ConcurrentHashMap<>();
    // showId -> seatId : LockInfo
    private static final long LOCK_DURATION_MS = 300_000;

    @Override
    public void lockSeats(long userId, long showId, List<Long> seatIds) {
        lockMap.putIfAbsent(showId, new ConcurrentHashMap<>());
        Map<Long, LockInfo> showLockInfos = lockMap.get(showId);

        long now = System.currentTimeMillis();
        for (Long seatId : seatIds) {
            showLockInfos.compute(seatId, (id, existing) -> {
                if (existing != null && !existing.isExpired(now)) {
                    throw new RuntimeException("Seat already locked: "+ seatId);
                }

                return new LockInfo(userId, now + LOCK_DURATION_MS);
            });
        }
    }

    @Override
    public void unlockSeats(long showId, List<Long> seatIds) {
        Map<Long, LockInfo> locks = lockMap.get(showId);

        if (locks != null) {
            seatIds.forEach(locks::remove);
        }
    }

    @Override
    public boolean validateLock(long userId, long showId, List<Long> seatIds) {
        Map<Long, LockInfo> lockInfoMap = lockMap.get(showId);

        if (lockInfoMap == null) {
            return false;
        }

        long now = System.currentTimeMillis();

        for (Long seatId : seatIds) {
            LockInfo info = lockInfoMap.get(seatId);

            if (info == null || info.isExpired(now) || userId != info.userId) {
                return false;
            }
        }

        return true;
    }

    private static class LockInfo {
        final long userId;
        final long expiryTime;

        public LockInfo(long userId, long expiryTime) {
            this.userId = userId;
            this.expiryTime = expiryTime;
        }

        boolean isExpired(long now) {
            return now > expiryTime;
        }
    }
}
