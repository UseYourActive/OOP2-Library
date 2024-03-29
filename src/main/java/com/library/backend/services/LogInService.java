package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.backend.exception.users.UserNotFoundException;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code LogInService} class provides functionality related to user authentication and login.
 * It interacts with the {@link com.library.database.repositories.UserRepository UserRepository}
 * to retrieve user information based on the provided username.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a LogInService instance with a UserRepository
 * LogInService logInService = new LogInService(UserRepository.getInstance());
 *
 * // Attempt to retrieve a user by username
 * User user = new User("exampleUsername", "password");
 * try {
 *     User retrievedUser = logInService.getUser(user);
 *     // Perform actions with the retrieved user
 * } catch (UserNotFoundException e) {
 *     // Handle the case where the user is not found
 * } catch (HibernateException e) {
 *     // Handle Hibernate-related exceptions
 * }
 * }
 * </pre>
 * In this example, the {@code LogInService} instance is created, and the {@code getUser} method is
 * used to retrieve a user by username. If the user is found, the retrieved user is returned; otherwise,
 * a {@link UserNotFoundException UserNotFoundException} is thrown.
 * <p>
 * The {@code LogInService} class implements the {@link com.library.backend.services.Service Service}
 * interface, providing a common interface for various services in the application.
 *
 * @see com.library.backend.services.Service
 * @see UserNotFoundException
 * @see com.library.database.entities.User
 * @see com.library.database.repositories.UserRepository
 */
public class LogInService implements Service {

    private final Logger logger = LoggerFactory.getLogger(LogInService.class);

    /**
     * The repository responsible for accessing user data from the database.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a {@code LogInService} instance with the specified {@link com.library.database.repositories.UserRepository UserRepository}.
     *
     * @param userRepository The repository used for accessing user data.
     */
    public LogInService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by username and validates the provided password against the stored password.
     *
     * @param username The username of the user to retrieve.
     * @param password The provided password for validation.
     * @return The retrieved user if authentication is successful.
     * @throws UserNotFoundException If the user with the specified username is not found.
     * @throws HibernateException    If a Hibernate-related exception occurs during user retrieval.
     */
    public User getUser(String username, String password) throws UserNotFoundException, HibernateException {

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        logger.info("Attempting to retrieve user: {}", username);

        User retrievedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", username);
                    return new UserNotFoundException("User not found");
                });

        String storedPassword = retrievedUser.getPassword();

        if (isPasswordMatch(user.getPassword(), storedPassword)) {
            logger.info("User retrieved successfully: {}", retrievedUser.getUsername());
            return retrievedUser;
        } else {
            logger.error("Invalid password for user: {}", username);
            throw new UserNotFoundException("Invalid password");
        }
    }

    /**
     * Compares the provided password with the stored password using BCrypt or plain comparison.
     *
     * @param providedPassword The password provided for validation.
     * @param storedPassword   The stored password for comparison.
     * @return {@code true} if the passwords match, {@code false} otherwise.
     */
    private boolean isPasswordMatch(String providedPassword, String storedPassword) {
        if (storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$")) {
            BCrypt.Result result = BCrypt.verifyer().verify(providedPassword.toCharArray(), storedPassword);
            return result.verified;
        } else {
            return providedPassword.equals(storedPassword);
        }
    }
}
