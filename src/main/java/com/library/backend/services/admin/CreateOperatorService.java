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

public class CreateOperatorService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(CreateOperatorService.class);
    private final UserRepository userRepository;
    @Setter private StrongPasswordValidator strongPasswordValidator;

    public CreateOperatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
        strongPasswordValidator = new StrongPasswordValidator();
    }

    public void createOperator(String username, String password, String repeatPassword) throws UserExistException, IncorrectInputException, UserNotFoundException {
        try {
            checkOperatorFieldsInput(username, password, repeatPassword);

            if (userRepository.findByUsername(username).isPresent())
                   throw new UserExistException("User with this username already exists");

        } catch (IncorrectInputException e) {
            logger.error("Input validation failed during operator creation", e);
            throw e;
        }catch (UserNotFoundException ignored){
            User operator = User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.OPERATOR)
                    .build();

            userRepository.save(operator);
            logger.info("Operator creation successful for username: '{}'", username);
        }
    }

    private void checkOperatorFieldsInput(String username, String password, String repeatPassword) throws IncorrectInputException {
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
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
