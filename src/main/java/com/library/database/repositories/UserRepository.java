package com.library.database.repositories;

import com.library.backend.exception.UserNotFoundException;
import com.library.database.entities.User;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static volatile UserRepository instance;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<User> findById(Long id) throws HibernateException {
        try {
            logger.info("Finding user by ID: {}", id);
            User user = super.session.get(User.class, id);
            if (user != null) {
                logger.info("Successfully found user with ID: {}", id);
            } else {
                logger.info("No user found with ID: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(user);
        } catch (HibernateException e) {
            logger.error("Error finding user by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<User> findAll() throws HibernateException {
        try {
            logger.info("Finding all users");
            List<User> users = super.session.createQuery("SELECT u FROM User u", User.class).getResultList();
            logger.info("Found {} users", users.size());
            return users;
        } catch (HibernateException e) {
            logger.error("Error retrieving all users", e);
            throw e;
        }
    }

    @Override
    public User getById(Long id) throws HibernateException {
        try {
            logger.info("Successfully found user with ID: {}", id);
            return super.session.get(User.class, id);
        } catch (HibernateException e) {
            logger.error("Error getting user by ID: {}", id, e);
            throw e;
        }
    }

    public Optional<User> findByUsername(String username) throws HibernateException {
        try {
            logger.info("Finding user by username: {}", username);
            User user = super.session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (user != null) {
                logger.info("Successfully found user by username: {}", username);
            } else {
                logger.error("User not found with username: {}", username);
                //throw new UserNotFoundException("User not found");
            }

            return Optional.ofNullable(user);
        } catch (HibernateException e) {
            logger.error("Error finding user by username: {}", username, e);
            throw e;
        }
    }
}
