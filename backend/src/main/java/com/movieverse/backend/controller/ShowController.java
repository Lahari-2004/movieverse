package com.movieverse.backend.controller;

import com.movieverse.backend.dto.ShowRequest;
import com.movieverse.backend.dto.ShowResponse;
import com.movieverse.backend.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShowResponse createShow(
            @Valid @RequestBody ShowRequest request) {

        return showService.createShow(request);
    }

    @GetMapping
    public List<ShowResponse> getAllShows() {

        return showService.getAllShows();
    }

    @GetMapping("/{id}")
    public ShowResponse getShowById(
            @PathVariable Long id) {

        return showService.getShowById(id);
    }

    @GetMapping("/movie/{movieId}")
    public List<ShowResponse> getShowsByMovie(
            @PathVariable Long movieId) {

        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/theatre/{theatreId}")
    public List<ShowResponse> getShowsByTheatre(
            @PathVariable Long theatreId) {

        return showService.getShowsByTheatre(theatreId);
    }

    @GetMapping("/screen/{screenId}")
    public List<ShowResponse> getShowsByScreen(
            @PathVariable Long screenId) {

        return showService.getShowsByScreen(screenId);
    }

    @PutMapping("/{id}")
    public ShowResponse updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowRequest request) {

        return showService.updateShow(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);
    }
}