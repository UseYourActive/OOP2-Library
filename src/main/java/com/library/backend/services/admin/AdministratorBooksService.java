package com.library.backend.services.admin;

import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.utils.DialogUtils;
import jdk.jshell.execution.Util;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * The {@code AdministratorBooksService} class provides functionality for managing book inventories
 * and associated book forms within the administration module. It includes methods for searching book inventories,
 * removing inventories, updating book forms, and retrieving all book inventories.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create an AdministratorBooksService instance with BookInventoryRepository and BookFormRepository
 * AdministratorBooksService booksService = new AdministratorBooksService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance());
 *
 * // Search for book inventories based on a string
 * String searchString = "Java Programming";
 * Collection<BookInventory> searchResults = booksService.searchBookInventory(searchString);
 *
 * // Remove a book inventory
 * BookInventory inventoryToRemove = // obtain a BookInventory instance;
 * booksService.removeInventory(inventoryToRemove);
 *
 * // Retrieve all book inventories
 * List<BookInventory> allInventories = booksService.getAllBookInventories();
 * }
 * </pre>
 * In this example, an {@code AdministratorBooksService} instance is created with the necessary repositories,
 * and various methods are used to search, remove, and retrieve book inventories.
 * <p>
 * The {@code AdministratorBooksService} class implements the {@link com.library.backend.services.Service Service}
 * interface, providing a common interface for various services in the application.
 *
 * @see com.library.backend.services.Service
 * @see com.library.database.entities.BookInventory
 * @see com.library.database.repositories.BookInventoryRepository
 * @see com.library.database.entities.BookForm
 * @see com.library.database.repositories.BookFormRepository
 * @see com.library.backend.engines.SearchEngine
 * @see com.library.backend.exception.searchengine.SearchEngineException
 */
@Getter
public class AdministratorBooksService implements Service {

    private final static Logger logger = LoggerFactory.getLogger(AdministratorBooksService.class);

    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;

    @Setter
    private SearchEngine<BookInventory> searchEngine;

    /**
     * Constructs an {@code AdministratorBooksService} instance with the specified repositories.
     *
     * @param bookInventoryRepository The repository for accessing book inventory data.
     * @param bookFormRepository      The repository for accessing book form data.
     */
    public AdministratorBooksService(BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
        searchEngine = new BookInventorySearchEngine();
    }

    /**
     * Searches for book inventories based on the provided string using the configured {@link com.library.backend.engines.SearchEngine SearchEngine}.
     *
     * @param searchString The string to search for in book inventories.
     * @return A collection of book inventories matching the search criteria.
     * @throws SearchEngineException If there is an issue with the search engine.
     */
    public Collection<BookInventory> searchBookInventory(String searchString) throws SearchEngineException {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logger.info("Searching book inventories for: '{}'", searchString);
        return searchEngine.search(inventories, searchString);
    }

    /**
     * Removes the specified {@link com.library.database.entities.BookInventory BookInventory} along with its associated book forms.
     *
     * @param inventory The BookInventory instance to be removed.
     */
    public void removeInventory(BookInventory inventory) {
        updateBookForms(inventory.getBookList());

        performRepositoryOperation(() -> bookInventoryRepository.delete(inventory), "deleted", "BookInventory");
    }

    /**
     * Updates the associated {@link com.library.database.entities.BookForm BookForms} by removing specified books.
     *
     * @param bookList The list of books to be removed from book forms.
     */
    public void updateBookForms(List<Book> bookList) {
        try {
            for (BookForm bookForm : bookFormRepository.findAll()) {
                for (Book bookToRemove : bookList) {
                    bookForm.getBooks().remove(bookToRemove);
                }

                if (bookForm.getBooks().isEmpty()) {
                    bookFormRepository.delete(bookForm);
                    logger.info("Deleted book form: {}", bookForm);
                } else {
                    bookFormRepository.update(bookForm);
                    logger.info("Updated book form: {}", bookForm);
                }
            }
        } catch (LazyInitializationException ignored) {
        }
    }

    /**
     * Retrieves all {@link com.library.database.entities.BookInventory BookInventories} from the repository.
     *
     * @return A list containing all book inventories.
     */
    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logEntityRetrieval("book_inventories", inventories.size());
        return inventories;
    }

    private void logEntityRetrieval(String entityName, int size) {
        logger.info("Retrieved {} {}: {}", size, entityName, (size == 1 ? "entity" : "entities"));
    }

    /**
     * Performs a repository operation and logs the result.
     *
     * @param repositoryOperation The operation to be performed on the repository.
     * @param action              The action being performed (e.g., created, updated, deleted).
     * @param entityName          The name of the entity being operated on.
     * @param <T>                 The type of result from the repository operation.
     */
    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

    public void loadNotifications(){
        List<Book> bookList = new ArrayList<>();

        bookInventoryRepository.findAll().forEach(bookInventory -> bookList.addAll(bookInventory.getBookList().stream().filter(book -> book.getBookStatus()== BookStatus.DAMAGED).toList()));


        StringBuilder stringBuilder=new StringBuilder();
        for(Book book:bookList){
            stringBuilder.append("Title").append(book.getTitle()).append("\tID").append(book.getId()).append(" ").append(book.getBookStatus()).append("\n");;
        }

        DialogUtils.showInfo("Damaged books",stringBuilder.toString());
    }
}
