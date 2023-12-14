package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;

import java.util.Arrays;

public class RegisterService implements Service{
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user){
        BCrypt.Hasher passwordEncryptor = BCrypt.with(BCrypt.Version.VERSION_2A);

        String hashedPassword = Arrays.toString(passwordEncryptor.hash(12, user.getPassword().toCharArray()));
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
