package com.sanket.aicontentguard.ai.exception;

public class AiServiceException
        extends RuntimeException {

    public AiServiceException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}