package com.sanket.aicontentguard.controller;

import com.sanket.aicontentguard.dto.LoginRequestDTO;
import com.sanket.aicontentguard.dto.LoginResponseDTO;
import com.sanket.aicontentguard.dto.RegisterRequestDTO;
import com.sanket.aicontentguard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequestDTO request) {

        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request) {

        return userService.loginUser(request);
    }
}