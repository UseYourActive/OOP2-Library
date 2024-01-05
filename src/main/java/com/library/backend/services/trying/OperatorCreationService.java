package com.library.backend.services.trying;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.frontend.utils.validators.StrongPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorCreationService { // CreateOperatorController
    private static final Logger logger = LoggerFactory.getLogger(OperatorCreationService.class);

    private final AdminService adminService;
    private final StrongPasswordValidator passwordValidator;

    public OperatorCreationService() {
        this.adminService = ServiceFactory.getService(AdminService.class);
        this.passwordValidator = new StrongPasswordValidator();
    }

    public void createOperator(String username, String password, String repeatPassword) throws IncorrectInputException {
        try {
            checkInput(username, password, repeatPassword);

            User operator = User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.OPERATOR)
                    .build();

            adminService.registerOperator(operator);
            logger.info("Operator creation successful for username: '{}'", username);
        } catch (IncorrectInputException e) {
            logger.error("Input validation failed during operator creation", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during operator creation", e);
            throw e;
        }
    }

    private void checkInput(String username, String password, String repeatPassword) throws IncorrectInputException {
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            throw new IncorrectInputException("Please fill out all fields!");
        }

        if (!password.equals(repeatPassword)) {
            throw new IncorrectInputException("The passwords did not match!");
        }

        if (!passwordValidator.isValid(password)) {
            throw new IncorrectInputException("Password is not strong enough.");
        }
    }
}
