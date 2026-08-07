package com.movieverse.backend.dto;

import com.movieverse.backend.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BookingResponse {

    private Long id;

    private Long userId;

    private Long showId;

    private Long movieId;

    private String movieTitle;

    private Long theatreId;

    private String theatreName;

    private Long screenId;

    private String screenName;

    private List<String> seatNumbers;

    private Double totalAmount;

    private BookingStatus status;

    private LocalDateTime bookedAt;
}