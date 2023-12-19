package com.library.backend.services;

import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;

public class LogInService implements Service{
    private final UserRepository userRepository;


    public LogInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(User user) {
        return this.userRepository.findByUsername(user.getUsername()).orElseThrow(RuntimeException::new);
    }

}
