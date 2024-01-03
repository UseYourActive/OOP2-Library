package com.library.backend.engines;

import com.library.backend.exception.searchengine.BookInventorySearchEngineException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Search engine implementation for searching book inventories.
 */
public class BookInventorySearchEngine implements SearchEngine<BookInventory> {
    private static final Logger logger = LoggerFactory.getLogger(BookInventorySearchEngine.class);

    /**
     * Searches for book inventories based on a search string.
     *
     * @param items          List of book inventories to search.
     * @param stringToSearch Search string.
     * @return A list of book inventories matching the search criteria.
     * @throws SearchEngineException If an error occurs during the search operation.
     */
    @Override
    public List<BookInventory> search(List<BookInventory> items, String stringToSearch) throws SearchEngineException {
        try {
            return items.stream()
                    .filter(inventory -> containsSearchString(inventory.getBookList().get(0), stringToSearch))
                    .toList();
        } catch (Exception e) {
            logger.error("Error occurred during book inventory search", e);
            throw new BookInventorySearchEngineException("Error occurred during book inventory search", e);
        }
    }

    private boolean containsSearchString(Book book, String stringToSearch) {
        return book.getTitle().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                book.getAuthor().toString().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                book.getResume().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                book.getGenre().toString().toUpperCase().contains(stringToSearch.toUpperCase()) ||
                (book.getPublishYear() != null && book.getPublishYear().toString().contains(stringToSearch));
    }
}
