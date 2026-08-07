package com.movieverse.backend.service;

import com.movieverse.backend.dto.SeatRequest;
import com.movieverse.backend.dto.SeatResponse;

import java.util.List;

public interface SeatService {

    SeatResponse createSeat(SeatRequest request);

    List<SeatResponse> getAllSeats();

    SeatResponse getSeatById(Long id);

    List<SeatResponse> getSeatsByScreen(Long screenId);

    SeatResponse updateSeat(Long id, SeatRequest request);

    void deleteSeat(Long id);
}