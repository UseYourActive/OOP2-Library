package com.library.backend.services;

import com.library.backend.exception.UserNotFoundException;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;

public class AccessService implements Service{
    private final UserRepository userRepository;


    public AccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(User user) throws UserNotFoundException {
        return this.userRepository.findByUsername(user.getUsername());
    }

}
