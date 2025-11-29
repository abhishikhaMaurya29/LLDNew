import Model.*;
import Repository.SeatLockRepository;
import Repository.ShowSeatRepository;
import Repository.impl.*;
import Repository.service.BookingService;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Setup
        MovieRepository movieRepo = new MovieRepository();
        CinemaRepository cinemaRepo = new CinemaRepository();
        ScreenRepository screenRepo = new ScreenRepository();
        MovieShowRepository showRepo = new MovieShowRepository();
        ShowSeatRepository seatRepo = new InMemoryShowSeatRepository();
        SeatLockRepository lockService = new InMemorySeatLockRepository();
        BookingService bookingService = new BookingService(seatRepo, lockService);

        // Data setup
        Movie movie = new Movie(1, "Dangal", 160, 12);
        movieRepo.save(movie);

        Cinema cinema = new Cinema(10, "PVR Forum Mall", "Bangalore");
        cinemaRepo.save(cinema);

        Screen screen = new Screen(100, cinema.getId(), "Screen 1");
        screenRepo.save(screen);

        MovieShow show = new MovieShow(500, movie.getId(), screen.getId(),
                LocalDateTime.of(2025, 1, 1, 18, 30));
        showRepo.save(show);

        // Create seats
        seatRepo.save(new ShowSeat(show.getId(), 1));
        seatRepo.save(new ShowSeat(show.getId(), 2));
        seatRepo.save(new ShowSeat(show.getId(), 3));

        // User booking flow
        bookingService.lockSeats(10, show.getId(), Arrays.asList(1L, 2L));
        System.out.println(bookingService.confirmBooking(10, show.getId(), Arrays.asList(1L, 2L), 999));
        System.out.println("Booking successful!");
    }
}