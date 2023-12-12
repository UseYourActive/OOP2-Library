package com.library.backend.services;

import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;

public class RegisterService implements Service{
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user){
        userRepository.save(user);
    }
}
