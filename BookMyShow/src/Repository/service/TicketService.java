package Repository.service;

import Model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public class TicketService {
    private long idCounter = 1;

    public Ticket generateTicket(long bookingId, long showId, List<Long> seatIds) {
        return new Ticket(idCounter++, bookingId, showId, seatIds, LocalDateTime.now());
    }
}
