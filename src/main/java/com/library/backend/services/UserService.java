package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.common.base.Preconditions;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class UserService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = Preconditions.checkNotNull(userRepository, "userRepository cannot be null");
    }

    public void registerOperator(User operator) {
        hashAndSetPassword(operator);

        performRepositoryOperation(() -> userRepository.save(operator), "registered", operator.getUsername());
    }

    public void removeOperator(User operator) {
        userRepository.delete(operator);
        logger.info("Operator removed: {}", operator.getUsername());
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logEntityRetrieval("users", users.size());
        return users;
    }

    private void hashAndSetPassword(User user) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        String hashedPassword = hasher.hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashedPassword);
    }

    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

    private void logEntityRetrieval(String entityName, int size) {
        logger.info("Retrieved {} {}: {}", size, entityName, (size == 1 ? "entity" : "entities"));
    }
}
