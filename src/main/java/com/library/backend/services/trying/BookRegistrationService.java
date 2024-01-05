package com.library.backend.services.trying;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.AdminService;
import com.library.backend.services.ServiceFactory;
import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import com.library.database.repositories.AuthorRepository;
import com.library.database.repositories.BookInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BookRegistrationService { // RegisterNewBookController
    private static final Logger logger = LoggerFactory.getLogger(BookRegistrationService.class);
    private static final String LOG_SUCCESSFUL_REGISTRATION = "Book registration successful for title: '{}', author: '{}'";
    private static final String LOG_ERROR_DURING_REGISTRATION = "Error occurred during book registration";
    private static final String LOG_FAILED_VALIDATION = "Input validation failed";
    private final AdminService adminService;

    public BookRegistrationService() {
        this.adminService = ServiceFactory.getService(AdminService.class);
    }

    public void registerNewBook(String title, String author, String year, String resume, Genre genre, String amount) throws Exception {
        try {
            checkInput(title, author, genre, year, amount);

            int quantity = getQuantity(amount);

            BookInventory bookInventory = createOrRetrieveBookInventory(title, author, genre, quantity, year, resume);

            adminService.saveInventory(bookInventory);
            logger.info(LOG_SUCCESSFUL_REGISTRATION, title, author);
        } catch (Exception e) {
            logger.error(LOG_ERROR_DURING_REGISTRATION, e);
            throw e;
        }
    }

    private void checkInput(String title, String author, Genre genre, String year, String amount) throws IncorrectInputException {
        try {
            if (title.isEmpty())
                throw new IncorrectInputException("Please enter book title.");

            if (author.isEmpty())
                throw new IncorrectInputException("Please enter book author.");

            if (genre == null)
                throw new IncorrectInputException("Please choose the genre of the book.");

            int yearInt = Integer.parseInt(year);
            if (yearInt < 0)
                throw new IncorrectInputException("Year cannot be negative.");

            int amountInt = Integer.parseInt(amount);
            if (amountInt < 0)
                throw new IncorrectInputException("Amount cannot be negative.");
        } catch (Exception e) {
            logger.error(LOG_FAILED_VALIDATION, e);
            throw e;
        }
    }

    private int getQuantity(String amount) {
        if (amount.isEmpty())
            return 1;
        else
            return Integer.parseInt(amount);
    }

    private BookInventory createOrRetrieveBookInventory(String title, String author, Genre genre, int quantity, String year, String resume) {
        BookInventoryRepository bookInventoryRepository = BookInventoryRepository.getInstance();
        BookInventory bookInventory = BookInventory.builder().build();

        Book representativeBook = createOrRetrieveBook(title, author, genre, year, resume);

        boolean flag = true;

        for (BookInventory existingInventory : bookInventoryRepository.findAll()) {
            if (existingInventory.getRepresentiveBook().equalsBook(representativeBook)) {
                for (int i = 0; i < quantity; i++) {
                    Book book = createOrRetrieveBook(title, author, genre, year, resume);
                    book.setInventory(existingInventory);

                    adminService.saveBook(book);

                    existingInventory.addBook(book);
                }
                adminService.saveInventory(existingInventory);

                flag = false;
                break;
            }
        }

        if (flag) {
            List<Book> bookList = new ArrayList<>();
            for (int i = 0; i < quantity; i++) {
                Book book = createOrRetrieveBook(title, author, genre, year, resume);
                bookInventory.setRepresentiveBook(book);
                book.setInventory(bookInventory);

                adminService.saveBook(book);

                bookList.add(book);
            }

            bookInventory.setBookList(bookList);
        }

        return bookInventory;
    }

    private Book createOrRetrieveBook(String title, String author, Genre genre, String year, String resume) {
        AuthorRepository authorRepository = AuthorRepository.getInstance();

        Author authorEntity = authorRepository.findByName(author).orElseGet(() ->
                Author.builder()
                        .name(author)
                        .books(new ArrayList<>())
                        .build()
        );

        Book book = Book.builder()
                .title(title)
                .author(authorEntity)
                .genre(genre)
                .bookStatus(BookStatus.AVAILABLE)
                .numberOfTimesUsed(0)
                .resume("")
                .build();

        try {
            if (!year.isEmpty())
                book.setPublishYear(Year.parse(year));
        } catch (Exception e) {
            logger.warn("Failed to parse year '{}'. Defaulting to current year.", year);
        }

        if (!resume.isEmpty()) {
            book.setResume(resume);
        }

        return book;
    }
}
