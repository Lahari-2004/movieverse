package com.movieverse.backend.controller;

import com.movieverse.backend.dto.ScreenRequest;
import com.movieverse.backend.dto.ScreenResponse;
import com.movieverse.backend.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScreenResponse createScreen(
            @Valid @RequestBody ScreenRequest request) {

        return screenService.createScreen(request);
    }

    @GetMapping
    public List<ScreenResponse> getAllScreens() {

        return screenService.getAllScreens();
    }

    @GetMapping("/{id}")
    public ScreenResponse getScreenById(
            @PathVariable Long id) {

        return screenService.getScreenById(id);
    }

    @GetMapping("/theatre/{theatreId}")
    public List<ScreenResponse> getScreensByTheatre(
            @PathVariable Long theatreId) {

        return screenService.getScreensByTheatre(theatreId);
    }

    @PutMapping("/{id}")
    public ScreenResponse updateScreen(
            @PathVariable Long id,
            @Valid @RequestBody ScreenRequest request) {

        return screenService.updateScreen(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteScreen(
            @PathVariable Long id) {

        screenService.deleteScreen(id);
    }
}