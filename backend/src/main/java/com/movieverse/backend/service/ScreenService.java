package com.movieverse.backend.service;

import com.movieverse.backend.dto.ScreenRequest;
import com.movieverse.backend.dto.ScreenResponse;

import java.util.List;

public interface ScreenService {

    ScreenResponse createScreen(ScreenRequest request);

    List<ScreenResponse> getAllScreens();

    ScreenResponse getScreenById(Long id);

    List<ScreenResponse> getScreensByTheatre(Long theatreId);

    ScreenResponse updateScreen(Long id, ScreenRequest request);

    void deleteScreen(Long id);
}