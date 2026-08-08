package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.BookingRequest;
import com.movieverse.backend.dto.BookingResponse;
import com.movieverse.backend.entity.Booking;
import com.movieverse.backend.entity.BookingSeat;
import com.movieverse.backend.entity.Seat;
import com.movieverse.backend.entity.Show;
import com.movieverse.backend.entity.User;
import com.movieverse.backend.enums.BookingStatus;
import com.movieverse.backend.exception.BookingException;
import com.movieverse.backend.exception.ResourceNotFoundException;
import com.movieverse.backend.exception.UnauthorizedException;
import com.movieverse.backend.repository.BookingRepository;
import com.movieverse.backend.repository.BookingSeatRepository;
import com.movieverse.backend.repository.SeatRepository;
import com.movieverse.backend.repository.ShowRepository;
import com.movieverse.backend.repository.UserRepository;
import com.movieverse.backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        // Get logged-in user from JWT
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Find show
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found"));

        // Find and validate seats
        List<Seat> seats = new ArrayList<>();

        for (Long seatId : request.getSeatIds()) {

            Seat seat = seatRepository.findByIdForUpdate(seatId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Seat not found: " + seatId
                            ));

            // Check whether seat belongs to the show's screen
            if (!seat.getScreen().getId()
                    .equals(show.getScreen().getId())) {

                throw new BookingException(
                        "Seat " + seat.getSeatNumber()
                                + " does not belong to the show's screen"
                );
            }

            // Check whether seat is already booked for this show
            if (bookingSeatRepository
                    .existsByBookingShowIdAndSeatIdAndBookingStatusNot(
                            show.getId(),
                            seat.getId(),
                            BookingStatus.CANCELLED)) {

                throw new BookingException(
                        "Seat " + seat.getSeatNumber()
                                + " is already booked"
                );
            }

            seats.add(seat);
        }

        // Calculate total amount
        double totalAmount = seats.stream()
                .mapToDouble(Seat::getPrice)
                .sum();

        // Create booking
        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .totalAmount(totalAmount)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        Booking savedBooking =
                bookingRepository.save(booking);

        // Create BookingSeat records
        for (Seat seat : seats) {

            BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(savedBooking)
                    .seat(seat)
                    .build();

            bookingSeatRepository.save(bookingSeat);
        }

        return mapToResponse(savedBooking, seats);
    }

    @Override
    public List<BookingResponse> getMyBookings() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookingResponse getBookingById(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        // Make sure the booking belongs to the logged-in user
        if (!booking.getUser().getId().equals(user.getId())) {

            throw new UnauthorizedException(
                    "You are not authorized to access this booking"
            );
        }

        return mapToResponse(booking);
    }

    @Override
    public void cancelBooking(Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        // Make sure the booking belongs to the logged-in user
        if (!booking.getUser().getId().equals(user.getId())) {

            throw new UnauthorizedException(
                    "You are not authorized to cancel this booking"
            );
        }

        // Check if already cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {

            throw new BookingException(
                    "Booking is already cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }

    /**
     * Convert Booking entity to BookingResponse
     */
    private BookingResponse mapToResponse(Booking booking) {

        List<BookingSeat> bookingSeats =
                bookingSeatRepository.findByBookingId(
                        booking.getId()
                );

        List<Seat> seats = bookingSeats
                .stream()
                .map(BookingSeat::getSeat)
                .toList();

        return mapToResponse(booking, seats);
    }

    /**
     * Convert Booking + Seats to BookingResponse
     */
    private BookingResponse mapToResponse(
            Booking booking,
            List<Seat> seats) {

        Show show = booking.getShow();

        return BookingResponse.builder()
                .id(booking.getId())

                .userId(booking.getUser().getId())

                .showId(show.getId())

                .movieId(show.getMovie().getId())
                .movieTitle(show.getMovie().getTitle())

                .theatreId(show.getTheatre().getId())
                .theatreName(show.getTheatre().getName())

                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())

                .seatNumbers(
                        seats.stream()
                                .map(Seat::getSeatNumber)
                                .toList()
                )

                .totalAmount(booking.getTotalAmount())

                .status(booking.getStatus())

                .bookedAt(booking.getBookedAt())

                .build();
    }
}