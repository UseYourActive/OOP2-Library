package com.library.backend.services;

import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInService implements Service {
    private final Logger logger = LoggerFactory.getLogger(LogInService.class);

    private final UserRepository userRepository;

    public LogInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(User user) {
        String username = user.getUsername();
        logger.info("Attempting to retrieve user: {}", username);

        User retrievedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", username);
                    return new RuntimeException("User not found");
                });

        logger.info("User retrieved successfully: {}", retrievedUser.getUsername());
        return retrievedUser;
    }
}
