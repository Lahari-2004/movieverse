package com.movieverse.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScreenResponse {

    private Long id;
    private String name;
    private Integer totalSeats;
    private Long theatreId;
    private String theatreName;
}