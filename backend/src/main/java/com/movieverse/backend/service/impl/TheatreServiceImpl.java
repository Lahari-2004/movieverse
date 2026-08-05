package com.movieverse.backend.service.impl;

import com.movieverse.backend.dto.TheatreRequest;
import com.movieverse.backend.dto.TheatreResponse;
import com.movieverse.backend.entity.Theatre;
import com.movieverse.backend.exception.TheatreNotFoundException;
import com.movieverse.backend.repository.TheatreRepository;
import com.movieverse.backend.service.TheatreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    @Override
    public TheatreResponse addTheatre(TheatreRequest request){
        Theatre theatre=Theatre.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .totalScreens(request.getTotalScreens())
                .build();

        Theatre saved=theatreRepository.save(theatre);

        return TheatreResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .address(saved.getAddress())
                .city(saved.getCity())
                .state(saved.getState())
                .zipCode(saved.getZipCode())
                .totalScreens(saved.getTotalScreens())
                .build();
    }

    @Override
    public List<TheatreResponse> getAllTheatres(){
        return theatreRepository.findAll()
                .stream()
                .map(theatre-> TheatreResponse.builder()
                        .id(theatre.getId())
                        .name(theatre.getName())
                        .address(theatre.getAddress())
                        .city(theatre.getCity())
                        .state(theatre.getState())
                        .zipCode(theatre.getZipCode())
                        .totalScreens(theatre.getTotalScreens())
                        .build()
                ).toList();
    }

    @Override
    public TheatreResponse getTheatreById(Long id){
        Theatre theatre=theatreRepository.findById(id)
                .orElseThrow(()->
                        new TheatreNotFoundException("Theatre not found with id : "+id));
        return TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .city(theatre.getCity())
                .state(theatre.getState())
                .zipCode(theatre.getZipCode())
                .totalScreens(theatre.getTotalScreens())
                .build();
    }

    @Override
    public TheatreResponse updateTheatre(Long id, TheatreRequest request){
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() ->
                        new TheatreNotFoundException("Theatre not found with id : " + id));

        theatre.setName(request.getName());
        theatre.setAddress(request.getAddress());
        theatre.setCity(request.getCity());
        theatre.setState(request.getState());
        theatre.setZipCode(request.getZipCode());
        theatre.setTotalScreens(request.getTotalScreens());

        Theatre updated = theatreRepository.save(theatre);

        return TheatreResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .address(updated.getAddress())
                .city(updated.getCity())
                .state(updated.getState())
                .zipCode(updated.getZipCode())
                .totalScreens(updated.getTotalScreens())
                .build();
    }

    @Override
    public void deleteTheatre(Long id){
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() ->
                        new TheatreNotFoundException("Theatre not found with id : " + id));

        theatreRepository.delete(theatre);
    }

    @Override
    public List<TheatreResponse> getTheatresByCity(String city){
        return theatreRepository.findByCityIgnoreCase(city)
                .stream()
                .map(theatre -> TheatreResponse.builder()
                        .id(theatre.getId())
                        .name(theatre.getName())
                        .address(theatre.getAddress())
                        .city(theatre.getCity())
                        .state(theatre.getState())
                        .zipCode(theatre.getZipCode())
                        .totalScreens(theatre.getTotalScreens())
                        .build())
                .toList();

    }
}
