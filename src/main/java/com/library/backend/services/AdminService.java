package com.library.backend.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.OperatorSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.entities.User;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Role;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.UserRepository;
import com.library.frontend.utils.validators.StrongPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class AdminService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;

    public AdminService(BookRepository bookRepository, UserRepository userRepository, BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
    }

    public void saveBook(Book book) {
        performRepositoryOperation(() -> bookRepository.save(book), "saved", book.getTitle());
    }

    public void createOperator(String username,String password,String repeatPassword) throws Exception {
        try {
            checkOperatorFieldsInput(username, password, repeatPassword);

            if(userRepository.findByUsername(username).isPresent())
                throw new Exception("User with this username already exists");

            User operator = User.builder()
                    .username(username)
                    .password(password)
                    .role(Role.OPERATOR)
                    .build();



            userRepository.save(operator);
            logger.info("Operator creation successful for username: '{}'", username);
        } catch (IncorrectInputException e) {
            logger.error("Input validation failed during operator creation", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error during operator creation", e);
            throw e;
        }
    }

    private void checkOperatorFieldsInput(String username, String password, String repeatPassword) throws IncorrectInputException {
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            throw new IncorrectInputException("Please fill out all fields!");
        }

        if (!password.equals(repeatPassword)) {
            throw new IncorrectInputException("The passwords did not match!");
        }

        StrongPasswordValidator passwordValidator=new StrongPasswordValidator();

        if (!passwordValidator.isValid(password)) {
            throw new IncorrectInputException("Password is not strong enough.");
        }
    }

    public void removeOperator(User user) throws Exception {
        if (user.getRole() == Role.ADMIN) {
            throw new Exception("You can't remove administrators");
        }

        userRepository.delete(user);
        logger.info("Operator removed: {}", user.getUsername());

    }

    public void increaseBookQuantity(String quantityString,BookInventory bookInventory) throws NumberFormatException{
        int quantity = Integer.parseInt(quantityString);

        if (quantity<0)
            throw new NumberFormatException();

        Book representiveBook = bookInventory.getRepresentiveBook();

        for (int i = 0; i < quantity; i++) {
            Book book = new Book(representiveBook);
            book.setBookStatus(BookStatus.AVAILABLE);
            book.setNumberOfTimesUsed(0);

            bookRepository.save(book);
            bookInventory.addBook(book);
        }

        bookInventoryRepository.update(bookInventory);
    }

    public void removeSelectedBooks(BookInventory bookInventory,List<Book> booksToRemove){
        updateBookForms(booksToRemove);
        removeBooksAndUpdateRepresentiveBook(bookInventory, booksToRemove);
    }

    private void updateBookForms(List<Book> bookList) {
        for(BookForm bookForm:bookFormRepository.findAll()){
            for(Book bookToRemove:bookList){
                bookForm.getBooks().remove(bookToRemove);
            }
            bookFormRepository.update(bookForm);
        }
    }

    private void removeBooksAndUpdateRepresentiveBook(BookInventory bookInventory, List<Book> booksToRemove) {
        boolean flag = true;

        for (Book bookToRemove : booksToRemove) {
            if (bookInventory.getRepresentiveBook().equals(bookToRemove) && bookInventory.getBookList().size() == 1) {
                bookInventoryRepository.delete(bookInventory);

                flag = false;
                break;
            }

            if (bookInventory.getRepresentiveBook().equals(bookToRemove)) {
                List<Book> nonSelected = bookInventory.getBookList().stream()
                        .filter(book -> !booksToRemove.contains(book))
                        .toList();

                for (Book book : nonSelected) {
                    if (!book.equals(bookInventory.getRepresentiveBook())) {
                        bookInventory.setRepresentiveBook(book);
                        break;
                    }
                }
            }

            bookInventory.getBookList().remove(bookToRemove);
        }

        if (flag)
            bookInventoryRepository.save(bookInventory);

    }


    public void removeInventory(BookInventory inventory){
        updateBookForms(inventory.getBookList());

        performRepositoryOperation(() -> bookInventoryRepository.delete(inventory), "deleted", "");
    }


    public Collection<BookInventory> searchBookInventory(String string) throws SearchEngineException {

        List<BookInventory> inventories = bookInventoryRepository.findAll();
        SearchEngine<BookInventory> searchEngine = new BookInventorySearchEngine();
        return searchEngine.search(inventories, string);
    }

    public Collection<User> searchUser(String string) throws SearchEngineException{
        List<User> inventories = userRepository.findAll();
        SearchEngine<User> searchEngine = new OperatorSearchEngine();
        return searchEngine.search(inventories, string);
    }

    public void saveBookForm(BookForm bookForm){

        bookFormRepository.save(bookForm);
    }
    public List<BookForm> getAllBookForms(){
        return bookFormRepository.findAll();
    }

    public void registerOperator(User operator) {
        hashAndSetPassword(operator);

        performRepositoryOperation(() -> userRepository.save(operator), "registered", operator.getUsername());
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

}
