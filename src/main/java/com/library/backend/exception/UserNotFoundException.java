package com.library.backend.exception;

public class UserNotFoundException extends ProcessException {
    public UserNotFoundException(String message){
        super(message);
    }
}
