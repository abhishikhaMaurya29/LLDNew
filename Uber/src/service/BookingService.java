package service;

import model.Booking;
import model.Driver;
import model.Location;
import model.Rider;
import repository.BookingRepository;
import repository.DriverRepository;
import strategy.DriverMatchingStrategy;
import strategy.PricingStrategy;

import java.util.UUID;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final DriverRepository driverRepository;
    private final DriverMatchingStrategy driverMatchingStrategy;
    private final PricingStrategy pricingStrategy;

    public BookingService(BookingRepository bookingRepository, DriverRepository driverRepository,
                          DriverMatchingStrategy driverMatchingStrategy, PricingStrategy pricingStrategy) {
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
        this.driverMatchingStrategy = driverMatchingStrategy;
        this.pricingStrategy = pricingStrategy;
    }

    public synchronized Booking createBooking(Rider rider, Location pickup, Location drop) {
        Driver driver = driverMatchingStrategy.findDriver(pickup, driverRepository.getAllDriver());
        Double amount = pricingStrategy.calculateFare(pickup, drop);

        Booking booking = new Booking(UUID.randomUUID().toString(), rider, driver, amount);
        bookingRepository.save(booking);

        return booking;
    }
}