package com.library.backend.engines;

import com.library.backend.exception.searchengine.ReaderSearchEngineException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Search engine implementation for searching readers.
 */
public class ReaderSearchEngine implements SearchEngine<Reader> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderSearchEngine.class);

    /**
     * Searches for readers based on a search string.
     *
     * @param items          List of readers to search.
     * @param stringToSearch Search string.
     * @return A set of readers matching the search criteria.
     * @throws SearchEngineException If an error occurs during the search operation.
     */
    @Override
    public Set<Reader> search(List<Reader> items, String stringToSearch) throws SearchEngineException {
        try {
            return items.stream()
                    .filter(reader -> reader.getFirstName().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                            reader.getMiddleName().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                            reader.getLastName().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                            reader.getEmail().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                            reader.getPhoneNumber().contains(stringToSearch.toUpperCase()))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            logger.error("Error occurred during reader search", e);
            throw new ReaderSearchEngineException("Error occurred during reader search", e);
        }
    }
}
