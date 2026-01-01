package com.cts.auth_service.service;

import com.cts.auth_service.dtos.LoginDto;
import com.cts.auth_service.dtos.RegisterDto;
import com.cts.auth_service.entities.Role;
import com.cts.auth_service.entities.User;
import com.cts.auth_service.repository.RoleRepository;
import com.cts.auth_service.repository.UserRepository;
import com.cts.auth_service.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found."));
        user.setRoles(List.of(role));

        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public List<String> getUserRoles(String username) {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
    }

    @Override
    public String validateTokenAndGetUsername(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        return jwtTokenProvider.getUsername(token);
    }
}