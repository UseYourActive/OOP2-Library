package com.library.backend.services.admin;

import com.library.backend.engines.OperatorSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.database.repositories.UserRepository;
import com.library.frontend.utils.validators.StrongPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class OperatorControllerService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(OperatorControllerService.class);

    public OperatorControllerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> searchUser(String string) throws SearchEngineException {
        List<User> inventories = userRepository.findAll();
        SearchEngine<User> searchEngine = new OperatorSearchEngine();
        return searchEngine.search(inventories, string);
    }

    public void removeOperator(User user) throws Exception {
        if (user.getRole() == Role.ADMIN) {
            throw new Exception("You can't remove administrators");
        }

        userRepository.delete(user);
        logger.info("Operator removed: {}", user.getUsername());

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void createOperator(String username, String password, String repeatPassword) throws Exception {
        try {
            checkOperatorFieldsInput(username, password, repeatPassword);

            if(userRepository.findByUsername(username).isPresent())
                throw new Exception("User with this username already exists");

            User operator = User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.OPERATOR)
                    .build();



            userRepository.save(operator);
            logger.info("Operator creation successful for username: '{}'", username);
        } catch (IncorrectInputException e) {
            logger.error("Input validation failed during operator creation", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during operator creation", e);
            throw e;
        }
    }

    private void checkOperatorFieldsInput(String username, String password, String repeatPassword) throws IncorrectInputException {
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            throw new IncorrectInputException("Please fill out all fields!");
        }

        if (!password.equals(repeatPassword)) {
            throw new IncorrectInputException("The passwords did not match!");
        }

        StrongPasswordValidator passwordValidator=new StrongPasswordValidator();

        if (!passwordValidator.isValid(password)) {
            throw new IncorrectInputException("Password is not strong enough.");
        }
    }
}
