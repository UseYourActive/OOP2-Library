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

public class AdministratorOperatorsService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(AdministratorOperatorsService.class);
    @Getter private final UserRepository userRepository;

    public AdministratorOperatorsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> searchUser(String string) throws SearchEngineException {
        List<User> inventories = userRepository.findAll();
        SearchEngine<User> searchEngine = new OperatorSearchEngine();
        logger.info("Searching users with string: {}", string);
        return searchEngine.search(inventories, string);
    }

    public void removeOperator(User user) throws Exception {
        if (user.getRole() == Role.ADMIN) {
            logger.warn("Attempt to remove administrator: {}", user.getUsername());
            throw new Exception("You can't remove administrators");
        }

        userRepository.delete(user);
        logger.info("Operator removed: {}", user.getUsername());
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }
}
