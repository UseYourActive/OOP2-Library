package com.library.backend.services.testing;

import com.google.common.base.Preconditions;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class BookService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = Preconditions.checkNotNull(bookRepository, "BookRepository cannot be null");
    }

    public void saveBook(Book book) {
        performRepositoryOperation(() -> bookRepository.save(book), "saved", book.getTitle());
    }

    public void removeBook(Book book) {
        bookRepository.delete(book);
        logger.info("Book removed: {}", book.getTitle());
    }

    public void removeAll(List<Book> books) {
        bookRepository.deleteAll(books);
        logger.info("Books removed: {}", books.size());
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        logEntityRetrieval("books", books.size());
        return books;
    }

    private void updateBookStatus(Book book, BookStatus newStatus, String action) {
        if (book != null) {
            book.setBookStatus(newStatus);
            performRepositoryOperation(() -> bookRepository.save(book), action, book.getTitle());
        } else {
            logger.error("Cannot update book status. Book is null.");
        }
    }

    private void logEntityRetrieval(String entityName, int size) {
        logger.info("Retrieved {} {}: {}", size, entityName, (size == 1 ? "entity" : "entities"));
    }

    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }
}
