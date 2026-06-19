package com.sanket.aicontentguard.controller;

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
}