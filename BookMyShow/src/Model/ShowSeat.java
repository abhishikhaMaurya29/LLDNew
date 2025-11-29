package Model;

public class ShowSeat {
    private final long seatId;
    private final long showId;
    private Long bookingId;
    private SeatStatus seatStatus;

    public ShowSeat(long seatId, long showId) {
        this.seatId = seatId;
        this.showId = showId;
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    public long getSeatId() {
        return seatId;
    }

    public long getShowId() {
        return showId;
    }

    public long getBookingId() {
        return bookingId;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
}
