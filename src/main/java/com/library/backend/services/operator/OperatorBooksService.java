package com.library.backend.services.operator;

import com.google.common.base.Preconditions;
import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.LibraryException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.entities.EventNotification;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.EventNotificationRepository;
import com.library.frontend.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * The {@code OperatorBooksService} class provides services for managing book-related operations
 * and book inventory for operators in the library system.
 *
 * @see Service
 */
public class OperatorBooksService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(OperatorBooksService.class);
    private final BookFormRepository bookFormRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final EventNotificationRepository eventNotificationRepository;
    private final BookRepository bookRepository;
    @Setter private SearchEngine<BookInventory> bookInventorySearchEngine;

    @Getter private ObservableList<Book> selectedBooks;
    @Getter private List<BookForm> overdueBookForms;

    /**
     * Constructs an {@code OperatorBooksService} instance with the specified repositories.
     *
     * @param bookFormRepository        The repository for managing book forms.
     * @param bookInventoryRepository   The repository for managing book inventories.
     * @param eventNotificationRepository The repository for managing event notifications.
     * @param bookRepository           The repository for managing books.
     */
    public OperatorBooksService(BookFormRepository bookFormRepository, BookInventoryRepository bookInventoryRepository, EventNotificationRepository eventNotificationRepository, BookRepository bookRepository) {
        this.bookFormRepository = bookFormRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.eventNotificationRepository = eventNotificationRepository;
        this.bookRepository = bookRepository;
        bookInventorySearchEngine = new BookInventorySearchEngine();
    }

    /**
     * Initializes the list of selected books for book-related operations.
     */
    public void initializeSelectedBooks() {
        selectedBooks = FXCollections.observableArrayList();
    }

    /**
     * Sets the list of overdue book forms in the service.
     */
    public void setAllOverdueBooks() {
        overdueBookForms = bookFormRepository.findAll().stream().filter(BookForm::isOverdue).toList();
        logger.info("Retrieved {} overdue book forms from the repository.", overdueBookForms.size());
    }

    /**
     * Retrieves a list of all book inventories in the library.
     *
     * @return A list of book inventories.
     * @throws RuntimeException If an error occurs while retrieving book inventories.
     */
    public List<BookInventory> getAllBookInventories() {
        try {
            List<BookInventory> inventories = bookInventoryRepository.findAll();
            logger.info("Retrieved {} book inventories from the repository.", inventories.size());
            return inventories;
        } catch (Exception e) {
            logger.error("Failed to retrieve book inventories", e);
            throw new RuntimeException("Failed to retrieve book inventories", e);
        }
    }

    /**
     * Searches for book inventories based on the specified search string.
     *
     * @param stringToSearch The search string for book inventories.
     * @return A collection of matching book inventories.
     * @throws SearchEngineException If an error occurs during the search operation.
     */
    public Collection<BookInventory> searchBookInventory(String stringToSearch) throws SearchEngineException {
        try {
            return bookInventorySearchEngine.search(bookInventoryRepository.findAll(), stringToSearch);
        } catch (SearchEngineException e) {
            logger.error("Failed to search book inventories", e);
            throw new RuntimeException("Failed to search book inventories", e);
        }
    }

    /**
     * Updates the status of book forms based on their overdue status.
     */
    public void updateBookForms() {
        try {
            List<BookForm> bookForms = bookFormRepository.findAll();
            for (BookForm bookForm : bookForms) {
                if (bookForm.isPresent() && bookForm.isOverdue()) {
                    bookForm.setStatus(BookFormStatus.LATE);
                    EventNotification eventNotification = EventNotification.builder()
                            .user(SceneLoader.getUser())
                            .timestamp(LocalDateTime.now())
                            .message("The deadline for returning books of: " + bookForm.getReader().getFullName() + " has passed.")
                            .build();

                    eventNotificationRepository.save(eventNotification);
                    logger.info("Saved event notification: {}", eventNotification.getMessage());
                    bookFormRepository.save(bookForm);
                    logger.info("Saved updated book form: {}", bookForm.getId());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to update book forms", e);
            throw new RuntimeException("Failed to update book forms", e);
        }
    }

    /**
     * Adds the specified book to the list of selected books.
     *
     * @param book The book to be added to the list.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public void addSelectedBookToList(Book book) {
        try {
            if (!selectedBooks.contains(book))
                selectedBooks.add(book);
        } catch (Exception e) {
            logger.error("Failed to add selected book to the list", e);
            throw new RuntimeException("Failed to add selected book to the list", e);
        }
    }

    /**
     * Removes the specified book from the list of selected books.
     *
     * @param book The book to be removed from the list.
     * @throws RuntimeException If an error occurs during the operation.
     */
    public void removeFromSelectedBooks(Book book) {
        try {
            selectedBooks.remove(book);
        } catch (Exception e) {
            logger.error("Failed to remove book from selected books", e);
            throw new RuntimeException("Failed to remove book from selected books", e);
        }
    }

    /**
     * Archives the specified book by updating its status.
     *
     * @param book The book to be archived.
     * @throws LibraryException If an error occurs during the archive operation.
     */
    public void archiveBook(Book book) throws LibraryException{
        updateBookStatus(book, BookStatus.ARCHIVED, "archived");
    }

    /**
     * Updates the status of the specified book.
     *
     * @param book      The book whose status is to be updated.
     * @param newStatus The new status for the book.
     * @param action    The action being performed (e.g., "archived").
     * @throws LibraryException If an error occurs during the status update.
     */
    private void updateBookStatus(Book book, BookStatus newStatus, String action) throws LibraryException {
        try {
            Preconditions.checkNotNull(book, "Book cannot be null");
            book.setBookStatus(newStatus);
            boolean result = bookRepository.save(book);
            if (result) {
                logger.info("Book {} successfully: {}", action, book.getTitle());
            } else {
                logger.error("Failed to {} book: {}", action, book.getTitle());
            }
        } catch (Exception e) {
            logger.error("Failed to update book status", e);
            throw new LibraryException("Failed to archive book", e);
        }
    }
}
