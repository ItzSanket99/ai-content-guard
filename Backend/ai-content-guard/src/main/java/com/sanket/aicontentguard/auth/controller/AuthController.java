package com.sanket.aicontentguard.auth.controller;

import com.sanket.aicontentguard.auth.dto.LoginRequestDTO;
import com.sanket.aicontentguard.auth.dto.LoginResponseDTO;
import com.sanket.aicontentguard.auth.dto.RegisterRequestDTO;
import com.sanket.aicontentguard.auth.service.UserService;
import com.sanket.aicontentguard.common.response.ApiResponse;
import com.sanket.aicontentguard.common.response.ApiResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public ResponseEntity<ApiResponse<String>> register(
                @Valid @RequestBody RegisterRequestDTO request) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                    ApiResponseBuilder.success(
                            "User register Successfully",
                            userService.registerUser(request)
                    )

            );
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
                @Valid @RequestBody LoginRequestDTO request) {

            return ResponseEntity.ok(
                    ApiResponseBuilder.success(
                            "User Login Successfully",
                        userService.loginUser(request)

                    )
            );
        }
    }

