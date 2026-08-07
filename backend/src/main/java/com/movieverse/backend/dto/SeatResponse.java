package com.movieverse.backend.dto;

import com.movieverse.backend.enums.SeatType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {

    private Long id;
    private String seatNumber;
    private SeatType seatType;
    private Double price;
    private Long screenId;
    private String screenName;
}