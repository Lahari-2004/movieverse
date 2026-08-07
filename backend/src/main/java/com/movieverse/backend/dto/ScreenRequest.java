package com.movieverse.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ScreenRequest {

    @NotBlank(message = "Screen name is required")
    private String name;

    @NotNull(message = "Total seats is required")
    @Positive(message = "Total seats must be greater than zero")
    private Integer totalSeats;

    @NotNull(message = "Theatre ID is required")
    private Long theatreId;
}