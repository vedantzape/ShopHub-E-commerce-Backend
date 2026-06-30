package com.vedant.E_Commerce.Project.controller;

import com.vedant.E_Commerce.Project.dto.AuthResponse;
import com.vedant.E_Commerce.Project.dto.LoginRequest;
import com.vedant.E_Commerce.Project.entity.User;
import com.vedant.E_Commerce.Project.security.JwtUtil;
import com.vedant.E_Commerce.Project.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;

    public UserController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        String token = service.login(request.getEmail(), request.getPassword());
        User user = service.getProfile(request.getEmail());

        return new AuthResponse(token, "Login Successful", user.getRole());
    }

    @GetMapping("/profile")
    public User getProfile(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        return service.getProfile(email);
    }
}