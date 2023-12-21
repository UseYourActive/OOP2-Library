package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.database.entities.Book;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class AdminService implements Service {
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public AdminService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public boolean archiveBook(Book book) {
        book.setBookStatus(BookStatus.ARCHIVED);
        boolean result = bookRepository.save(book);
        if (result) {
            logger.info("Book archived: {}", book.getTitle());
        } else {
            logger.error("Failed to archive book: {}", book.getTitle());
        }
        return result;
    }

    public boolean saveBook(Book book) {
        boolean result = bookRepository.save(book);
        if (result) {
            logger.info("Book saved: {}", book.getTitle());
        } else {
            logger.error("Failed to save book: {}", book.getTitle());
        }
        return result;
    }

    public void removeBook(Book book) {
        bookRepository.delete(book);
        logger.info("Book removed: {}", book.getTitle());
    }

    public boolean registerOperator(User operator) {
        BCrypt.Hasher passwordEncryptor = BCrypt.with(BCrypt.Version.VERSION_2A);
        String hashedPassword = Arrays.toString(passwordEncryptor.hash(12, operator.getPassword().toCharArray()));
        operator.setPassword(hashedPassword);

        boolean result = userRepository.save(operator);
        if (result) {
            logger.info("Operator registered: {}", operator.getUsername());
        } else {
            logger.error("Failed to register operator: {}", operator.getUsername());
        }
        return result;
    }

    public void removeOperator(User operator) {
        userRepository.delete(operator);
        logger.info("Operator removed: {}", operator.getUsername());
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users", users.size());
        return users;
    }

    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();
        logger.info("Retrieved {} books", books.size());
        return books;
    }
}
