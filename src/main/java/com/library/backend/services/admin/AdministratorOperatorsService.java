package com.library.backend.services.admin;

import com.library.backend.engines.OperatorSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.Service;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.database.repositories.UserRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * The {@code AdministratorOperatorsService} class provides functionality for managing operators
 * (non-administrator users) within the administration module. It includes methods for searching operators,
 * removing operators, and retrieving all users.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create an AdministratorOperatorsService instance with UserRepository
 * AdministratorOperatorsService operatorsService = new AdministratorOperatorsService(UserRepository.getInstance());
 *
 * // Search for operators based on a string
 * String searchString = "John Doe";
 * Collection<User> searchResults = operatorsService.searchUser(searchString);
 *
 * // Remove an operator
 * User operatorToRemove = // obtain a User instance;
 * try {
 *     operatorsService.removeOperator(operatorToRemove);
 *     // Operator removed successfully
 * } catch (Exception e) {
 *     // Handle the exception (e.g., trying to remove an administrator)
 * }
 *
 * // Retrieve all users
 * List<User> allUsers = operatorsService.getAllUsers();
 * }
 * </pre>
 * In this example, an {@code AdministratorOperatorsService} instance is created with the necessary repository,
 * and various methods are used to search, remove, and retrieve operators.
 * <p>
 * The {@code AdministratorOperatorsService} class implements the {@link com.library.backend.services.Service Service}
 * interface, providing a common interface for various services in the application.
 *
 * @see com.library.backend.services.Service
 * @see com.library.database.entities.User
 * @see com.library.database.repositories.UserRepository
 * @see com.library.backend.engines.SearchEngine
 * @see com.library.backend.exception.searchengine.SearchEngineException
 */
public class AdministratorOperatorsService implements Service {

    private static final Logger logger = LoggerFactory.getLogger(AdministratorOperatorsService.class);

    @Getter
    private final UserRepository userRepository;

    /**
     * Constructs an {@code AdministratorOperatorsService} instance with the specified UserRepository.
     *
     * @param userRepository The repository for accessing user data.
     */
    public AdministratorOperatorsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Searches for operators (non-administrator users) based on the provided string
     * using the configured {@link com.library.backend.engines.SearchEngine SearchEngine}.
     *
     * @param searchString The string to search for in user data.
     * @return A collection of users matching the search criteria.
     * @throws SearchEngineException If there is an issue with the search engine.
     */
    public Collection<User> searchUser(String searchString) throws SearchEngineException {
        List<User> users = userRepository.findAll();
        SearchEngine<User> searchEngine = new OperatorSearchEngine();
        logger.info("Searching users with string: {}", searchString);
        return searchEngine.search(users, searchString);
    }

    /**
     * Removes the specified {@link com.library.database.entities.User User} (operator).
     *
     * @param user The User instance to be removed.
     * @throws Exception If there is an issue during the removal, such as attempting to remove an administrator.
     */
    public void removeOperator(User user) throws Exception {
        if (user.getRole() == Role.ADMIN) {
            logger.warn("Attempt to remove administrator: {}", user.getUsername());
            throw new Exception("You can't remove administrators");
        }

        userRepository.delete(user);
        logger.info("Operator removed: {}", user.getUsername());
    }

    /**
     * Retrieves all {@link com.library.database.entities.User Users} from the repository.
     *
     * @return A list containing all users.
     */
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }
}
