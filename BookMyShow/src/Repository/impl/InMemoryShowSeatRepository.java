package Repository.impl;

import Model.SeatStatus;
import Model.ShowSeat;
import Repository.ShowSeatRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryShowSeatRepository implements ShowSeatRepository {
    Map<Long, List<ShowSeat>> store = new HashMap<>();

    public List<ShowSeat> getSeatsForShow(long showId, List<Long> seatIds) {
        return store.getOrDefault(showId, new ArrayList<>()).stream()
                .filter(s -> seatIds.contains(s.getSeatId()))
                .toList();
    }

    public void save(ShowSeat seat) {
        store.computeIfAbsent(seat.getShowId(), k -> new ArrayList<>()).add(seat);
    }

    public void saveAll(List<ShowSeat> seats) {
        for (ShowSeat showSeat : seats) {
            save(showSeat);
        }
    }

    public void markSeatBooked(long showId, List<Long> seatIds, long bookingId) {
        for (ShowSeat s : getSeatsForShow(showId, seatIds)) {
            s.setBookingId(bookingId);
            s.setSeatStatus(SeatStatus.BOOKED);
        }
    }

    public void markSeatAvailable(long showId, List<Long> seatIds) {
        for (ShowSeat s : getSeatsForShow(showId, seatIds)) {
            s.setBookingId(null);
            s.setSeatStatus(SeatStatus.AVAILABLE);
        }
    }
}
