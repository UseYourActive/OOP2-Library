package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class AdminService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AdminService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void archiveBook(Book book) {
        updateBookStatus(book, BookStatus.ARCHIVED, "archived");
    }

    public void saveBook(Book book) {
        performRepositoryOperation(() -> bookRepository.save(book), "saved", book.getTitle());
    }

    public void removeBook(Book book) {
        bookRepository.delete(book);
        logger.info("Book removed: {}", book.getTitle());
    }

    public void registerOperator(User operator) {
        hashAndSetPassword(operator);

        performRepositoryOperation(() -> userRepository.save(operator), "registered", operator.getUsername());
    }

    public void removeOperator(User operator) {
        userRepository.delete(operator);
        logger.info("Operator removed: {}", operator.getUsername());
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        logEntityRetrieval("users", users.size());
        return users;
    }

    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();
        logEntityRetrieval("books", books.size());
        return books;
    }

    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

    private void logEntityRetrieval(String entityName, int size) {
        logger.info("Retrieved {} {}: {}", size, entityName, (size == 1 ? "entity" : "entities"));
    }

    private void hashAndSetPassword(User user) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        String hashedPassword = hasher.hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashedPassword);
    }

    private void updateBookStatus(Book book, BookStatus newStatus, String action) {
        if (book != null) {
            book.setBookStatus(newStatus);
            performRepositoryOperation(() -> bookRepository.save(book), action, book.getTitle());
        } else {
            logger.error("Cannot update book status. Book is null.");
        }
    }
}
