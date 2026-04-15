package com.transport.auth_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transport.auth_service.dto.AuthResponseDTO;
import com.transport.auth_service.dto.LoginRequestDTO;
import com.transport.auth_service.model.User;
import com.transport.auth_service.repository.UserRepository;
import com.transport.auth_service.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponseDTO(token);
    }

    public void register(LoginRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        System.out.println("INPUT PASSWORD: " + dto.getPassword());
        System.out.println("DB PASSWORD: " + user.getPassword());
        userRepository.save(user);
    }
}