package Model;

import java.time.LocalDateTime;
import java.util.List;

public class Ticket {
    private final long ticketId;
    private final long bookingId;
    private final long showId;
    private final List<Long> seatIds;
    private final LocalDateTime bookingTime;

    public Ticket(long ticketId, long bookingId, long showId, List<Long> seatIds, LocalDateTime bookingTime) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.showId = showId;
        this.seatIds = seatIds;
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", bookingId=" + bookingId +
                ", showId=" + showId +
                ", seatIds=" + seatIds +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
