package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.SeatRequest;
import com.movieverse.backend.dto.SeatResponse;
import com.movieverse.backend.entity.Screen;
import com.movieverse.backend.entity.Seat;
import com.movieverse.backend.exception.ResourceNotFoundException;
import com.movieverse.backend.repository.ScreenRepository;
import com.movieverse.backend.repository.SeatRepository;
import com.movieverse.backend.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    @Override
    public SeatResponse createSeat(SeatRequest request) {

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .seatType(request.getSeatType())
                .price(request.getPrice())
                .screen(screen)
                .build();

        Seat savedSeat = seatRepository.save(seat);

        return mapToResponse(savedSeat);
    }

    @Override
    public List<SeatResponse> getAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SeatResponse getSeatById(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

        return mapToResponse(seat);
    }

    @Override
    public List<SeatResponse> getSeatsByScreen(Long screenId) {

        if (!screenRepository.existsById(screenId)) {
            throw new ResourceNotFoundException("Screen not found");
        }

        return seatRepository.findByScreenId(screenId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SeatResponse updateSeat(Long id, SeatRequest request) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        seat.setSeatNumber(request.getSeatNumber());
        seat.setSeatType(request.getSeatType());
        seat.setPrice(request.getPrice());
        seat.setScreen(screen);

        Seat updatedSeat = seatRepository.save(seat);

        return mapToResponse(updatedSeat);
    }

    @Override
    public void deleteSeat(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found"));

        seatRepository.delete(seat);
    }

    private SeatResponse mapToResponse(Seat seat) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .price(seat.getPrice())
                .screenId(seat.getScreen().getId())
                .screenName(seat.getScreen().getName())
                .build();
    }
}