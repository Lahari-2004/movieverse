package com.movieverse.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowResponse {

    private Long id;

    private Long movieId;
    private String movieTitle;

    private Long theatreId;
    private String theatreName;

    private Long screenId;
    private String screenName;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double ticketPrice;
}