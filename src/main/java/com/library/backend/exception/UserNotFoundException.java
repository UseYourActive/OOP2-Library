package com.library.backend.exception;

public class UserNotFoundException extends LibraryException {
    public UserNotFoundException(String message){
        super(message);
    }
}
