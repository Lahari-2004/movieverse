package com.movieverse.backend.controller;

import com.movieverse.backend.dto.SeatRequest;
import com.movieverse.backend.dto.SeatResponse;
import com.movieverse.backend.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeatResponse createSeat(
            @Valid @RequestBody SeatRequest request) {

        return seatService.createSeat(request);
    }

    @GetMapping
    public List<SeatResponse> getAllSeats() {

        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public SeatResponse getSeatById(
            @PathVariable Long id) {

        return seatService.getSeatById(id);
    }

    @GetMapping("/screen/{screenId}")
    public List<SeatResponse> getSeatsByScreen(
            @PathVariable Long screenId) {

        return seatService.getSeatsByScreen(screenId);
    }

    @PutMapping("/{id}")
    public SeatResponse updateSeat(
            @PathVariable Long id,
            @Valid @RequestBody SeatRequest request) {

        return seatService.updateSeat(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeat(
            @PathVariable Long id) {

        seatService.deleteSeat(id);
    }
}