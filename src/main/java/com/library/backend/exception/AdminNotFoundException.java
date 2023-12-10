package com.library.backend.exception;

public class AdminNotFoundException extends UserNotFoundException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
