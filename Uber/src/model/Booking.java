package model;

public class Booking {
    private final String id;
    private final Rider rider;
    private final Driver driver;
    private BookingStatus bookingStatus;
    private final double amount;

    public Booking(String id, Rider rider, Driver driver, Double amount) {
        this.id = id;
        this.rider = rider;
        this.driver = driver;
        this.amount = amount;
    }

    public void start() {
        bookingStatus = BookingStatus.IN_PROGRESS;
    }

    public void completeTrip() {
        bookingStatus = BookingStatus.COMPLETED;
        driver.release();
    }

    public String getId() {
        return id;
    }
}