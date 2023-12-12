package com.library.database.repositories;

import com.library.backend.exception.UserNotFoundException;
import com.library.database.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public Optional<User> findById(Long id) {
        logger.info("Finding user by ID: {}", id);
        User user = super.session.get(User.class, id);
        logger.info("User found by ID {}: {}", id, user);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        logger.info("Finding all users");
        List<User> users = super.session.createQuery("SELECT u FROM User u", User.class).getResultList();
        logger.info("Found {} users", users.size());
        return users;
    }

    public User findByUsername(String username) throws UserNotFoundException {
        logger.info("Finding user by username: {}", username);
        User user = super.session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();

        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new UserNotFoundException("User not found");
        }

        logger.info("User found by username {}: {}", username, user);
        return user;
    }
}
