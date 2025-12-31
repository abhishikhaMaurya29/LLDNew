package controller;

import model.Booking;
import model.Location;
import model.Rider;
import service.BookingService;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Booking bookCab(Rider rider, Location pickup, Location drop) {
        return bookingService.createBooking(rider, pickup, drop);
    }
}