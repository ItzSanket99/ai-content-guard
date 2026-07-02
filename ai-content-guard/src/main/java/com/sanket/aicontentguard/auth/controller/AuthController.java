package com.sanket.aicontentguard.auth.controller;

import com.sanket.aicontentguard.auth.dto.LoginRequestDTO;
import com.sanket.aicontentguard.auth.dto.LoginResponseDTO;
import com.sanket.aicontentguard.auth.dto.RegisterRequestDTO;
import com.sanket.aicontentguard.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

