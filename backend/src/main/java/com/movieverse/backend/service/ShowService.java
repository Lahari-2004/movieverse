package com.movieverse.backend.service;

import com.movieverse.backend.dto.ShowRequest;
import com.movieverse.backend.dto.ShowResponse;

import java.util.List;

public interface ShowService {

    ShowResponse createShow(ShowRequest request);

    List<ShowResponse> getAllShows();

    ShowResponse getShowById(Long id);

    List<ShowResponse> getShowsByMovie(Long movieId);

    List<ShowResponse> getShowsByTheatre(Long theatreId);

    List<ShowResponse> getShowsByScreen(Long screenId);

    ShowResponse updateShow(Long id, ShowRequest request);

    void deleteShow(Long id);
}