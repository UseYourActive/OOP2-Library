package com.library.backend.engines;

import com.library.backend.exception.searchengine.SearchEngineException;

import java.util.Collection;
import java.util.List;

/**
 * Generic interface for search engines.
 *
 * @param <T> Type of items to be searched.
 */
public interface SearchEngine<T> {
    /**
     * Searches for items based on a search string.
     *
     * @param items          List of items to search.
     * @param stringToSearch Search string.
     * @return A collection of items matching the search criteria.
     * @throws SearchEngineException If an error occurs during the search operation.
     */
    Collection<T> search(List<T> items, String stringToSearch) throws SearchEngineException;
}
