package com.movieverse.backend.controller;

import com.movieverse.backend.dto.TheatreRequest;
import com.movieverse.backend.dto.TheatreResponse;
import com.movieverse.backend.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TheatreResponse addTheatre(@Valid @RequestBody TheatreRequest request){
        return theatreService.addTheatre(request);
    }

    @GetMapping
    public List<TheatreResponse> getAllTheatres(){
        return theatreService.getAllTheatres();
    }

    @GetMapping("/{id}")
    public TheatreResponse getTheatreById(@PathVariable  Long id){
        return theatreService.getTheatreById(id);

    }

    @PutMapping("/{id}")
    public TheatreResponse updateTheatre(@PathVariable Long id, @Valid @RequestBody TheatreRequest request){
        return theatreService.updateTheatre(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTheatre(@PathVariable Long id){
        theatreService.deleteTheatre(id);
    }

    @GetMapping("/city/{city}")
    public List<TheatreResponse> getTheatresByCity(@PathVariable String city){
        return  theatreService.getTheatresByCity(city);
    }

}
