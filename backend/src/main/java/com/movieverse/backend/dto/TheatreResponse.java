package com.movieverse.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TheatreResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private Integer totalScreens;
}
