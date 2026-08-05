package com.movieverse.backend.service;

import com.movieverse.backend.dto.TheatreRequest;
import com.movieverse.backend.dto.TheatreResponse;

import java.util.List;

public interface TheatreService {
    TheatreResponse addTheatre(TheatreRequest request);
    List<TheatreResponse> getAllTheatres();
    TheatreResponse getTheatreById(Long id);
    TheatreResponse updateTheatre(Long id, TheatreRequest request);
    void deleteTheatre(Long id);
    List<TheatreResponse> getTheatresByCity(String city);
}
