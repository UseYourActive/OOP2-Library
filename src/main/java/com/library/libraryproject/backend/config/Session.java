package com.library.libraryproject.backend.config;

import com.library.libraryproject.database.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Session {
    private static Session instance;

    private User user;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
}
