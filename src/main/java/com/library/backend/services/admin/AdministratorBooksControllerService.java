package com.library.backend.services.admin;

import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Getter
public class AdministratorBooksControllerService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(AdministratorBooksControllerService.class);
    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;
    private final SearchEngine<BookInventory> searchEngine;

    public AdministratorBooksControllerService(BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
        searchEngine = new BookInventorySearchEngine();
    }

    public Collection<BookInventory> searchBookInventory(String string) throws SearchEngineException {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logger.info("Searching book inventories for: '{}'", string);
        return searchEngine.search(inventories, string);
    }

    public void removeInventory(BookInventory inventory) {
        updateBookForms(inventory.getBookList());

        performRepositoryOperation(() -> bookInventoryRepository.delete(inventory), "deleted", "BookInventory");
    }

    private void updateBookForms(List<Book> bookList) {
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
    }

    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logEntityRetrieval("book_inventories", inventories.size());
        return inventories;
    }

    private void logEntityRetrieval(String entityName, int size) {
        logger.info("Retrieved {} {}: {}", size, entityName, (size == 1 ? "entity" : "entities"));
    }
}
