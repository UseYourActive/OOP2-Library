package com.library.database.repositories;

import com.library.backend.exception.users.UserNotFoundException;
import com.library.database.entities.User;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserRepository} class provides access to user-related database operations using Hibernate as the underlying
 * ORM (Object-Relational Mapping) framework. It extends the generic {@link Repository} class and serves as a singleton
 * repository for managing instances of the {@link com.library.database.entities.User} entity.
 *
 * <p>The class supports basic CRUD (Create, Read, Update, Delete) operations for users, including finding a user by ID,
 * retrieving all users, getting a user by ID, and finding a user by username.</p>
 *
 * <p>Usage of this repository requires obtaining an instance through the {@link #getInstance()} method. The class is
 * designed as a thread-safe singleton to ensure consistent access across the application.</p>
 *
 * <p>The class inherits the common logging and exception handling mechanisms from its parent {@link Repository} class.</p>
 *
 * @see Repository
 * @see User
 * @see HibernateException
 * @see Logger
 */
public class UserRepository extends Repository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private static volatile UserRepository instance;

    private UserRepository() {
    }

    /**
     * Get the singleton instance of the {@code UserRepository} class. This method follows the double-check idiom for lazy
     * initialization, ensuring that only one instance of the repository is created in a multi-threaded environment.
     *
     * @return The singleton instance of the {@code UserRepository}.
     */
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

    /**
     * Find and return a user by its unique identifier (ID). If the user is not found, an empty {@code Optional} is returned.
     *
     * @param id The ID of the user to find.
     * @return An {@code Optional} containing the found user, or empty if the user is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Retrieve and return a list of all users. This method uses Hibernate Query Language (HQL) to execute a query
     * fetching all users.
     *
     * @return A list containing all users.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Get and return a user by its unique identifier (ID). If the user is not found, it returns null.
     *
     * @param id The ID of the user to get.
     * @return The found user, or null if the user is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Find and return a user by its username. If the user is not found, an empty {@code Optional} is returned.
     *
     * @param username The username of the user to find.
     * @return An {@code Optional} containing the found user, or empty if the user is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     * @throws UserNotFoundException If the user is not found.
     */
    public Optional<User> findByUsername(String username) throws HibernateException, UserNotFoundException {
        try {
            logger.info("Finding user by username: {}", username);
            User user = super.session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (user != null) {
                logger.info("Successfully found user by username: {}", username);
            } else {
                logger.error("User not found with username: {}", username);
                throw new UserNotFoundException("User not found");
            }

            return Optional.ofNullable(user);
        } catch (HibernateException e) {
            logger.error("Error finding user by username: {}", username, e);
            throw e;
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
