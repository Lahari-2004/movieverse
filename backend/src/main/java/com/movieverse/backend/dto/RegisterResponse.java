package com.movieverse.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String message;
    
}
