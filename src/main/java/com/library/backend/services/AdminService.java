package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookInventoryRepository;
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
    private final BookInventoryRepository bookInventoryRepository;

    public AdminService(BookRepository bookRepository, UserRepository userRepository,BookInventoryRepository bookInventoryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookInventoryRepository=bookInventoryRepository;
    }

    public void archiveBook(Book book) {
        updateBookStatus(book, BookStatus.ARCHIVED, "archived");
    }

    public void saveBook(Book book) {
        performRepositoryOperation(() -> bookRepository.save(book), "saved", book.getTitle());
    }

    public void removeBook(BookInventory inventory) {
        //Book book=inventory.getBook();
        //bookRepository.delete(book);

        bookInventoryRepository.delete(inventory);
       // logger.info("Book removed: {}", book.getTitle());
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        logEntityRetrieval("books", books.size());
        return books;
    }

    public void registerOperator(User operator) {
        hashAndSetPassword(operator);

        performRepositoryOperation(() -> userRepository.save(operator), "registered", operator.getUsername());
    }

    public void removeOperator(User operator) {
        userRepository.delete(operator);
        logger.info("Operator removed: {}", operator.getUsername());
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        logEntityRetrieval("users", users.size());
        return users;
    }



    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logEntityRetrieval("book_inventories", inventories.size());
        return inventories;
    }

    public void saveInventory(BookInventory bookInventory){
        performRepositoryOperation(() -> bookInventoryRepository.save(bookInventory), "saved", "");
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
