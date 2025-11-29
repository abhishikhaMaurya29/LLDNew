package Repository.service;

import Model.SeatStatus;
import Model.ShowSeat;
import Model.Ticket;
import Repository.SeatLockRepository;
import Repository.ShowSeatRepository;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final ShowSeatRepository showSeatRepository;
    private final SeatLockRepository seatLockRepository;
    private final TicketService ticketService;

    public BookingService(ShowSeatRepository showSeatRepository, SeatLockRepository seatLockRepository) {
        this.showSeatRepository = showSeatRepository;
        this.seatLockRepository = seatLockRepository;
        this.ticketService = new TicketService();
    }

    public void lockSeats(long userId, long showId, List<Long> seatIds) {
        List<ShowSeat> showSeats = showSeatRepository.getSeatsForShow(showId, seatIds);
        List<Long> bookedSeatIds = new ArrayList<>();

        for (ShowSeat showSeat : showSeats) {
            if (SeatStatus.BOOKED.equals(showSeat.getSeatStatus())) {
                bookedSeatIds.add(showSeat.getSeatId());
            }
        }

        if (!bookedSeatIds.isEmpty()) {
            throw new RuntimeException("Already booked: " + bookedSeatIds);
        }
        seatLockRepository.lockSeats(userId, showId, seatIds);
        showSeats.forEach(s -> s.setSeatStatus(SeatStatus.LOCKED));
        showSeatRepository.saveAll(showSeats);
    }

    public Ticket confirmBooking(long userId, long showId, List<Long> seatIds, long bookingId) {
        if (!seatLockRepository.validateLock(userId, showId, seatIds))
            throw new RuntimeException("Seat lock expired");

        showSeatRepository.markSeatBooked(showId, seatIds, bookingId);
        seatLockRepository.unlockSeats(showId, seatIds);

        return ticketService.generateTicket(bookingId, showId, seatIds);
    }
}