package com.library.backend.services.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.UserExistException;
import com.library.backend.exception.users.UserNotFoundException;
import com.library.backend.services.Service;
import com.library.backend.validators.StrongPasswordValidator;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.database.repositories.UserRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code CreateOperatorService} class provides functionality to create operator accounts.
 * It includes input validation and checks for existing usernames.
 * Strong password validation is also performed before creating an operator.
 *
 * @see Service
 */
public class CreateOperatorService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(CreateOperatorService.class);
    private final UserRepository userRepository;
    @Setter private StrongPasswordValidator strongPasswordValidator;

    /**
     * Constructs a {@code CreateOperatorService} instance with the specified UserRepository.
     *
     * @param userRepository The repository for managing user data.
     */
    public CreateOperatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
        strongPasswordValidator = new StrongPasswordValidator();
    }

    /**
     * Creates a new operator account with the provided username, password, and repeat password.
     * Validates input fields, checks for existing usernames, and enforces strong password criteria.
     *
     * @param username        The username for the new operator.
     * @param password        The password for the new operator.
     * @param repeatPassword  The repeated password for confirmation.
     * @throws UserExistException      If a user with the same username already exists.
     * @throws IncorrectInputException If the input validation fails.
     * @throws UserNotFoundException   If an unexpected error occurs during user creation.
     */
    public void createOperator(String username, String password, String repeatPassword)
            throws UserExistException, IncorrectInputException, UserNotFoundException {
        try {
            checkOperatorFieldsInput(username, password, repeatPassword);

            if (userRepository.findByUsername(username).isPresent())
                throw new UserExistException("User with this username already exists");

        } catch (IncorrectInputException e) {
            logger.error("Input validation failed during operator creation", e);
            throw e;
        } catch (UserNotFoundException ignored) {
            User operator = User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.OPERATOR)
                    .build();

            userRepository.save(operator);
            logger.info("Operator creation successful for username: '{}'", username);
        }
    }

    /**
     * Validates the input fields for creating an operator, including username, password, and repeat password.
     * Checks for empty fields, matching passwords, and enforces strong password criteria.
     *
     * @param username       The username for the new operator.
     * @param password       The password for the new operator.
     * @param repeatPassword The repeated password for confirmation.
     * @throws IncorrectInputException If the input validation fails.
     */
    private void checkOperatorFieldsInput(String username, String password, String repeatPassword)
            throws IncorrectInputException {
        if (username == null || password == null || repeatPassword == null || username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            logger.error("Please fill out all fields!");
            throw new IncorrectInputException("Please fill out all fields!");
        }

        if (!password.equals(repeatPassword)) {
            logger.error("The passwords did not match!");
            throw new IncorrectInputException("The passwords did not match!");
        }

        if (!strongPasswordValidator.isValid(password)) {
            logger.error("Password is not strong enough.");
            throw new IncorrectInputException("Password is not strong enough.");
        }
    }
}

