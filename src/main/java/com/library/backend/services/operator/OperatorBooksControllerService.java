package com.library.backend.services.operator;

import com.google.common.base.Preconditions;
import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
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
import com.library.frontend.utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class OperatorBooksControllerService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(OperatorBooksControllerService.class);
    private final BookFormRepository bookFormRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final EventNotificationRepository eventNotificationRepository;
    private final BookRepository bookRepository;
    @Getter
    private ObservableList<Book> selectedBooks;
    private SearchEngine<BookInventory> bookInventorySearchEngine;
    @Getter
    private List<BookForm> overdueBookForms;

    public OperatorBooksControllerService(BookFormRepository bookFormRepository, BookInventoryRepository bookInventoryRepository, EventNotificationRepository eventNotificationRepository, BookRepository bookRepository) {
        this.bookFormRepository = bookFormRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.eventNotificationRepository = eventNotificationRepository;
        this.bookRepository = bookRepository;
        bookInventorySearchEngine = new BookInventorySearchEngine();
    }

    public void initializeSelectedBooks() {
        selectedBooks = FXCollections.observableArrayList();
    }

    public void setAllOverdueBooks() {
        overdueBookForms = bookFormRepository.findAll().stream().filter(BookForm::isOverdue).toList();
        logger.info("Retrieved {} overdue book forms from the repository.", overdueBookForms.size());
    }

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

    public Collection<BookInventory> searchBookInventory(String stringToSearch) throws SearchEngineException {
        try {
            return bookInventorySearchEngine.search(bookInventoryRepository.findAll(), stringToSearch);
        } catch (SearchEngineException e) {
            logger.error("Failed to search book inventories", e);
            throw new RuntimeException("Failed to search book inventories", e);
        }
    }

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

    public void addSelectedBookToList(Book book) {
        try {
            if (!selectedBooks.contains(book))
                selectedBooks.add(book);
        } catch (Exception e) {
            logger.error("Failed to add selected book to the list", e);
            throw new RuntimeException("Failed to add selected book to the list", e);
        }
    }

    public void removeFromSelectedBooks(Book book) {
        try {
            selectedBooks.remove(book);
        } catch (Exception e) {
            logger.error("Failed to remove book from selected books", e);
            throw new RuntimeException("Failed to remove book from selected books", e);
        }
    }

    public void archiveBook(Book book) {
        try {
            updateBookStatus(book, BookStatus.ARCHIVED, "archived");
        } catch (Exception e) {
            logger.error("Failed to archive book", e);
            throw new RuntimeException("Failed to archive book", e);
        }
    }

    private void updateBookStatus(Book book, BookStatus newStatus, String action) {
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
            throw new RuntimeException("Failed to update book status", e);
        }
    }
}
