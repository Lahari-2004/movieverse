package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.ShowRequest;
import com.movieverse.backend.dto.ShowResponse;
import com.movieverse.backend.entity.Movie;
import com.movieverse.backend.entity.Screen;
import com.movieverse.backend.entity.Show;
import com.movieverse.backend.entity.Theatre;
import com.movieverse.backend.exception.ResourceNotFoundException;
import com.movieverse.backend.repository.MovieRepository;
import com.movieverse.backend.repository.ScreenRepository;
import com.movieverse.backend.repository.ShowRepository;
import com.movieverse.backend.repository.TheatreRepository;
import com.movieverse.backend.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    @Override
    public ShowResponse createShow(ShowRequest request) {

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        // Make sure the screen actually belongs to the selected theatre
        if (!screen.getTheatre().getId().equals(theatre.getId())) {
            throw new IllegalArgumentException(
                    "Screen does not belong to the selected theatre"
            );
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        Show show = Show.builder()
                .movie(movie)
                .theatre(theatre)
                .screen(screen)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .ticketPrice(request.getTicketPrice())
                .build();

        Show savedShow = showRepository.save(show);

        return mapToResponse(savedShow);
    }

    @Override
    public List<ShowResponse> getAllShows() {

        return showRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ShowResponse getShowById(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found"));

        return mapToResponse(show);
    }

    @Override
    public List<ShowResponse> getShowsByMovie(Long movieId) {

        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie not found");
        }

        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ShowResponse> getShowsByTheatre(Long theatreId) {

        if (!theatreRepository.existsById(theatreId)) {
            throw new ResourceNotFoundException("Theatre not found");
        }

        return showRepository.findByTheatreId(theatreId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ShowResponse> getShowsByScreen(Long screenId) {

        if (!screenRepository.existsById(screenId)) {
            throw new ResourceNotFoundException("Screen not found");
        }

        return showRepository.findByScreenId(screenId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ShowResponse updateShow(Long id, ShowRequest request) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found"));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        if (!screen.getTheatre().getId().equals(theatre.getId())) {
            throw new IllegalArgumentException(
                    "Screen does not belong to the selected theatre"
            );
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setTicketPrice(request.getTicketPrice());

        Show updatedShow = showRepository.save(show);

        return mapToResponse(updatedShow);
    }

    @Override
    public void deleteShow(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found"));

        showRepository.delete(show);
    }

    private ShowResponse mapToResponse(Show show) {

        return ShowResponse.builder()
                .id(show.getId())

                .movieId(show.getMovie().getId())
                .movieTitle(show.getMovie().getTitle())

                .theatreId(show.getTheatre().getId())
                .theatreName(show.getTheatre().getName())

                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())

                .startTime(show.getStartTime())
                .endTime(show.getEndTime())

                .ticketPrice(show.getTicketPrice())

                .build();
    }
}