package com.movieverse.backend.controller;

import com.movieverse.backend.dto.BookingRequest;
import com.movieverse.backend.dto.BookingResponse;
import com.movieverse.backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(
            @Valid @RequestBody BookingRequest request) {

        return bookingService.createBooking(request);
    }

    @GetMapping
    public List<BookingResponse> getMyBookings() {

        return bookingService.getMyBookings();
    }

    @GetMapping("/{id}")
    public BookingResponse getBookingById(
            @PathVariable Long id) {

        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}/cancel")
    public String cancelBooking(
            @PathVariable Long id) {

        bookingService.cancelBooking(id);

        return "Booking cancelled successfully";
    }
}