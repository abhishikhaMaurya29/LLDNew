package repository;

import model.Booking;

import java.util.concurrent.ConcurrentHashMap;

public class BookingRepository {
    private final ConcurrentHashMap<String, Booking> bookings = new ConcurrentHashMap<>();

    public void save(Booking booking) {
        bookings.put(booking.getId(), booking);
    }
}
