package com.sanket.aicontentguard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponseDTO {

    private Long userId;

    private String name;

    private String email;

    private String role;

    private String message;
}