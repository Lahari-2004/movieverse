package com.movieverse.backend.repository;

import com.movieverse.backend.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

    List<BookingSeat> findByBookingId(Long bookingId);

    boolean existsByBookingShowIdAndSeatIdAndBookingStatusNot(
            Long showId,
            Long seatId,
            com.movieverse.backend.enums.BookingStatus status
    );
}