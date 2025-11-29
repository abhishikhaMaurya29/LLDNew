package Repository;

import java.util.List;

public interface SeatLockRepository {
    void lockSeats(long userId, long showId, List<Long> seatIds);

    void unlockSeats(long showId, List<Long> seatIds);

    boolean validateLock(long userId, long showId, List<Long> seatIds);
}
