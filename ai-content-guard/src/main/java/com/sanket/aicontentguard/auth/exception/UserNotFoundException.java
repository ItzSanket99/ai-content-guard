package com.sanket.aicontentguard.auth.exception;

public class UserNotFoundException
extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}