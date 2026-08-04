package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.MovieRequest;
import com.movieverse.backend.dto.MovieResponse;
import com.movieverse.backend.entity.Movie;
import com.movieverse.backend.exception.MovieNotFoundException;
import com.movieverse.backend.repository.MovieRepository;
import com.movieverse.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieResponse addMovie(MovieRequest request) {

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .duration(request.getDuration())
                .releaseDate(request.getReleaseDate())
                .posterUrl(request.getPosterUrl())
                .trailerUrl(request.getTrailerUrl())
                .rating(request.getRating())
                .build();

        Movie savedMovie = movieRepository.save(movie);

        return MovieResponse.builder()
                .id(savedMovie.getId())
                .title(savedMovie.getTitle())
                .description(savedMovie.getDescription())
                .genre(savedMovie.getGenre())
                .language(savedMovie.getLanguage())
                .duration(savedMovie.getDuration())
                .releaseDate(savedMovie.getReleaseDate())
                .posterUrl(savedMovie.getPosterUrl())
                .trailerUrl(savedMovie.getTrailerUrl())
                .rating(savedMovie.getRating())
                .build();
    }

    @Override
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(movie -> MovieResponse.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .genre(movie.getGenre())
                        .language(movie.getLanguage())
                        .duration(movie.getDuration())
                        .releaseDate(movie.getReleaseDate())
                        .posterUrl(movie.getPosterUrl())
                        .trailerUrl(movie.getTrailerUrl())
                        .rating(movie.getRating())
                        .build())
                .toList();
    }

    @Override
    public MovieResponse getMovieById(Long id) {

        Movie movie = movieRepository.findById(id).orElseThrow(()->

                new MovieNotFoundException("Movie not found with id : " + id));

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .duration(movie.getDuration())
                .releaseDate(movie.getReleaseDate())
                .posterUrl(movie.getPosterUrl())
                .trailerUrl(movie.getTrailerUrl())
                .rating(movie.getRating())
                .build();
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest request) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException("Movie not found with id: " + id));

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDuration(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setRating(request.getRating());

        Movie updatedMovie = movieRepository.save(movie);

        return MovieResponse.builder()
                .id(updatedMovie.getId())
                .title(updatedMovie.getTitle())
                .description(updatedMovie.getDescription())
                .genre(updatedMovie.getGenre())
                .language(updatedMovie.getLanguage())
                .duration(updatedMovie.getDuration())
                .releaseDate(updatedMovie.getReleaseDate())
                .posterUrl(updatedMovie.getPosterUrl())
                .trailerUrl(updatedMovie.getTrailerUrl())
                .rating(updatedMovie.getRating())
                .build();
    }

    @Override
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException("Movie not found with id: " + id));

        movieRepository.delete(movie);
    }
}