package com.library.backend.exception;

public class ReaderNotFoundException extends UserNotFoundException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}
