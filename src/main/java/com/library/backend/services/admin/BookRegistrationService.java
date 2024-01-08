package com.library.backend.services.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.Service;
import com.library.database.entities.Author;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Genre;
import com.library.database.repositories.AuthorRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BookRegistrationService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(BookRegistrationService.class);
    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepository;

    public BookRegistrationService(BookInventoryRepository bookInventoryRepository, BookRepository bookRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookRepository = bookRepository;
    }

    public void registerNewBook(String title, String author, String year, String resume, Genre genre, String amount) throws IncorrectInputException, NumberFormatException {
        checkInput(title, author, genre, year, amount, resume);

        int quantity = getQuantity(amount);

        BookInventory bookInventory = createBookInventory(title, author, genre, quantity, year, resume);

        saveInventory(bookInventory);
        logger.info("Book registration successful for title: '{}', author: '{}'", title, author);
    }

    private void checkInput(String title, String author, Genre genre, String year, String amount, String resume) throws IncorrectInputException, NumberFormatException {
        try {
            if (title == null || title.isEmpty())
                throw new IncorrectInputException("Please enter book title.");

            if (author == null || author.isEmpty() || (containsNumbers(author)))
                throw new IncorrectInputException("Please enter book author.");

            if (genre == null)
                throw new IncorrectInputException("Please choose the genre of the book.");

            if (resume == null || resume.isEmpty())
                throw new IncorrectInputException("Please write the resume of the book.");

            int yearInt = Integer.parseInt(year);
            if (yearInt < 0)
                throw new IncorrectInputException("Year cannot be negative.");

            int amountInt = Integer.parseInt(amount);
            if (amountInt < 0)
                throw new IncorrectInputException("Amount cannot be negative.");
        } catch (IncorrectInputException | NumberFormatException e) {
            logger.error("Incorrect input!", e);
            throw e;
        }
    }

    private boolean containsNumbers(String input) {
        return input.matches(".*\\d+.*");
    }

    private int getQuantity(String amount) {
        if (amount.isEmpty())
            return 1;
        else
            return Integer.parseInt(amount);
    }

    private BookInventory createBookInventory(String title, String author, Genre genre, int quantity, String year, String resume) {
        BookInventoryRepository bookInventoryRepository = BookInventoryRepository.getInstance();
        BookInventory bookInventory = BookInventory.builder().build();

        Book representativeBook = createBook(title, author, genre, year, resume);

        boolean flag = true;

        for (BookInventory existingInventory : bookInventoryRepository.findAll()) {
            if (existingInventory.getRepresentiveBook().equalsBook(representativeBook)) {
                for (int i = 0; i < quantity; i++) {
                    Book book = createBook(title, author, genre, year, resume);
                    book.setInventory(existingInventory);

                    saveBook(book);

                    existingInventory.addBook(book);
                }
                saveInventory(existingInventory);

                flag = false;
                break;
            }
        }

        if (flag) {
            List<Book> bookList = new ArrayList<>();
            for (int i = 0; i < quantity; i++) {
                Book book = createBook(title, author, genre, year, resume);
                bookInventory.setRepresentiveBook(book);
                book.setInventory(bookInventory);

                saveBook(book);

                bookList.add(book);
            }

            bookInventory.setBookList(bookList);
        }

        return bookInventory;
    }

    private Book createBook(String title, String author, Genre genre, String year, String resume) {
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

    public void saveInventory(BookInventory bookInventory) {
        performRepositoryOperation(() -> bookInventoryRepository.save(bookInventory), "saved", "BookInventory");
    }

    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

    public void saveBook(Book book) {
        performRepositoryOperation(() -> bookRepository.save(book), "saved", "Book");
    }
}
