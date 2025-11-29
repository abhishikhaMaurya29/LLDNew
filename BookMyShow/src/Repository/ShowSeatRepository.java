package Repository;

import Model.ShowSeat;

import java.util.List;

public interface ShowSeatRepository {
    List<ShowSeat> getSeatsForShow(long showId, List<Long> seatIds);

    void save(ShowSeat seat);

    void saveAll(List<ShowSeat> seats);

    void markSeatBooked(long showId, List<Long> seatIds, long bookingId);

    void markSeatAvailable(long showId, List<Long> seatIds);
}
