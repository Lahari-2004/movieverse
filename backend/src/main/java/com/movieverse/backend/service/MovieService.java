package com.movieverse.backend.service;

import com.movieverse.backend.dto.MovieRequest;
import com.movieverse.backend.dto.MovieResponse;

import java.util.List;

public interface MovieService {
    MovieResponse addMovie(MovieRequest request);

    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id);

    MovieResponse updateMovie(Long id, MovieRequest request);

    void deleteMovie(Long id);
}
