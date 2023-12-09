package com.library.backend.exception.entities;

import com.library.backend.exception.LibraryException;

public class UserException extends LibraryException {
    public UserException(String message){
        super(message);
    }
}
