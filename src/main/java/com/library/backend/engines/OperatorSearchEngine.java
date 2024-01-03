package com.library.backend.engines;

import com.library.backend.exception.searchengine.OperatorSearchEngineException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Search engine implementation for searching operators.
 */
public class OperatorSearchEngine implements SearchEngine<User> {
    private static final Logger logger = LoggerFactory.getLogger(OperatorSearchEngine.class);

    /**
     * Searches for operators based on a search string.
     *
     * @param items          List of operators to search.
     * @param stringToSearch Search string.
     * @return A set of operators matching the search criteria.
     * @throws SearchEngineException If an error occurs during the search operation.
     */
    @Override
    public Set<User> search(List<User> items, String stringToSearch) throws SearchEngineException {
        try {
            return items.stream()
                    .filter(user -> user.getUsername().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                            user.getRole().toString().toUpperCase().contains(stringToSearch.toUpperCase()))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            logger.error("Error occurred during operator search", e);
            throw new OperatorSearchEngineException("Error occurred during operator search", e);
        }
    }
}
