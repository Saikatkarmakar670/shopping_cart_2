package com.cts.auth_service.service;

import com.cts.auth_service.dtos.LoginDto;
import com.cts.auth_service.dtos.RegisterDto;
import java.util.List;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    List<String> getUserRoles(String username);
    String validateTokenAndGetUsername(String token);
}