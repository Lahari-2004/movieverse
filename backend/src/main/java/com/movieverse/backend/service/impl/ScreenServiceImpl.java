package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.ScreenRequest;
import com.movieverse.backend.dto.ScreenResponse;
import com.movieverse.backend.entity.Screen;
import com.movieverse.backend.entity.Theatre;
import com.movieverse.backend.exception.ResourceNotFoundException;
import com.movieverse.backend.repository.ScreenRepository;
import com.movieverse.backend.repository.TheatreRepository;
import com.movieverse.backend.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    @Override
    public ScreenResponse createScreen(ScreenRequest request) {

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found"));

        Screen screen = Screen.builder()
                .name(request.getName())
                .totalSeats(request.getTotalSeats())
                .theatre(theatre)
                .build();

        Screen savedScreen = screenRepository.save(screen);

        return mapToResponse(savedScreen);
    }

    @Override
    public List<ScreenResponse> getAllScreens() {

        return screenRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ScreenResponse getScreenById(Long id) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        return mapToResponse(screen);
    }

    @Override
    public List<ScreenResponse> getScreensByTheatre(Long theatreId) {

        if (!theatreRepository.existsById(theatreId)) {
            throw new ResourceNotFoundException("Theatre not found");
        }

        return screenRepository.findByTheatreId(theatreId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ScreenResponse updateScreen(Long id, ScreenRequest request) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found"));

        screen.setName(request.getName());
        screen.setTotalSeats(request.getTotalSeats());
        screen.setTheatre(theatre);

        Screen updatedScreen = screenRepository.save(screen);

        return mapToResponse(updatedScreen);
    }

    @Override
    public void deleteScreen(Long id) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found"));

        screenRepository.delete(screen);
    }

    private ScreenResponse mapToResponse(Screen screen) {

        return ScreenResponse.builder()
                .id(screen.getId())
                .name(screen.getName())
                .totalSeats(screen.getTotalSeats())
                .theatreId(screen.getTheatre().getId())
                .theatreName(screen.getTheatre().getName())
                .build();
    }
}