package com.cts.auth_service.controller;

import com.cts.auth_service.dtos.AuthResponse;
import com.cts.auth_service.dtos.LoginDto;
import com.cts.auth_service.dtos.RegisterDto;
import com.cts.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // UC-1: Register User [cite: 138-144]
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // UC-2: Authenticate User (Login) [cite: 149-155]
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(token);
        return ResponseEntity.ok(authResponse);
    }
}