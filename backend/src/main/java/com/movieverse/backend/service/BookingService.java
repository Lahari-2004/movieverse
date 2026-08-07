package com.movieverse.backend.service;

import com.movieverse.backend.dto.BookingRequest;
import com.movieverse.backend.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    List<BookingResponse> getMyBookings();

    BookingResponse getBookingById(Long id);

    void cancelBooking(Long id);
}