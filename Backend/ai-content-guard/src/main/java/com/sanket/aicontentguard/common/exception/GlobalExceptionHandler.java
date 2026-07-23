package com.sanket.aicontentguard.common.exception;

import com.sanket.aicontentguard.ai.exception.AiServiceException;
import com.sanket.aicontentguard.common.response.ApiResponse;
import com.sanket.aicontentguard.common.response.ApiResponseBuilder;
import com.sanket.aicontentguard.auth.exception.InvalidCredentialsException;
import com.sanket.aicontentguard.auth.exception.ResourceAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleAlreadyExists(ResourceAlreadyExistsException ex){

        log.warn(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseBuilder.error(
                                ex.getMessage(),
                                HttpStatus.CONFLICT.value()
                        )
                );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleInvalidCredentials(InvalidCredentialsException ex){

        log.warn(ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ApiResponseBuilder.error(
                                ex.getMessage(),
                                HttpStatus.UNAUTHORIZED.value()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleValidation(MethodArgumentNotValidException ex){

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(
                        ApiResponseBuilder.error(
                                message,
                                HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>
    handleException(Exception ex){

        log.error("Unexpected error", ex);

        return ResponseEntity.internalServerError()
                .body(
                        ApiResponseBuilder.error(
                                "Something went wrong",
                                HttpStatus.INTERNAL_SERVER_ERROR.value()
                        )
                );
    }

    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleAiException(AiServiceException ex){

        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        ApiResponseBuilder.error(
                                ex.getMessage(),
                                HttpStatus.SERVICE_UNAVAILABLE.value()
                        )
                );
    }
}