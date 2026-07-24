package com.movieverse.backend.service;

import com.movieverse.backend.dto.LoginRequest;
import com.movieverse.backend.dto.LoginResponse;
import com.movieverse.backend.dto.RegisterRequest;
import com.movieverse.backend.dto.RegisterResponse;
import com.movieverse.backend.entity.User;


public interface UserService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
