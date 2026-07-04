package com.sanket.aicontentguard.common.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private int status;

    private LocalDateTime timestamp;

    private T data;
}