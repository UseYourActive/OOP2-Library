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

/**
 * The {@code BookRegistrationService} class provides functionality for registering new books
 * and managing the corresponding book inventory. It includes methods for validating input,
 * creating book entities, and saving book inventories and books.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a BookRegistrationService instance with BookInventoryRepository and BookRepository
 * BookRegistrationService registrationService = new BookRegistrationService(BookInventoryRepository.getInstance(), BookRepository.getInstance());
 *
 * // Register a new book
 * String title = "The Great Gatsby";
 * String author = "F. Scott Fitzgerald";
 * String year = "1925";
 * String resume = "A novel capturing the essence of the Jazz Age.";
 * Genre genre = Genre.FICTION;
 * String amount = "5";
 * try {
 *     registrationService.registerNewBook(title, author, year, resume, genre, amount);
 *     // Book registration successful
 * } catch (IncorrectInputException | NumberFormatException e) {
 *     // Handle incorrect input or number format exception
 * }
 * }
 * </pre>
 * In this example, a {@code BookRegistrationService} instance is created, and the {@code registerNewBook}
 * method is used to register a new book. If the registration is successful, the log will indicate success;
 * otherwise, an {@link IncorrectInputException} or {@link NumberFormatException} is thrown with details about the failure.
 * <p>
 * The {@code BookRegistrationService} class implements the {@link com.library.backend.services.Service Service}
 * interface, providing a common interface for various services in the application.
 *
 * @see com.library.backend.services.Service
 * @see com.library.backend.exception.IncorrectInputException
 * @see com.library.database.entities.Author
 * @see com.library.database.entities.Book
 * @see com.library.database.entities.BookInventory
 * @see com.library.database.enums.Genre
 * @see com.library.database.enums.BookStatus
 * @see com.library.database.repositories.AuthorRepository
 * @see com.library.database.repositories.BookInventoryRepository
 * @see com.library.database.repositories.BookRepository
 */
public class BookRegistrationService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(BookRegistrationService.class);
    private final BookInventoryRepository bookInventoryRepository;
    private final BookRepository bookRepository;

    /**
     * Constructs a {@code BookRegistrationService} instance with the specified BookInventoryRepository and BookRepository.
     *
     * @param bookInventoryRepository The repository for managing book inventories.
     * @param bookRepository          The repository for managing books.
     */
    public BookRegistrationService(BookInventoryRepository bookInventoryRepository, BookRepository bookRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Registers a new book with the specified details, including title, author, year, resume, genre, and quantity.
     *
     * @param title   The title of the new book.
     * @param author  The author of the new book.
     * @param year    The publication year of the new book.
     * @param resume  A summary or description of the new book.
     * @param genre   The genre of the new book.
     * @param amount  The quantity of the new book.
     * @throws IncorrectInputException If the input validation fails.
     * @throws NumberFormatException   If there is an issue parsing numeric input.
     */
    public void registerNewBook(String title, String author, String year, String resume, Genre genre, String amount) throws IncorrectInputException, NumberFormatException {
        checkInput(title, author, genre, year, amount, resume);

        int quantity = getQuantity(amount);

        BookInventory bookInventory = createBookInventory(title, author, genre, quantity, year, resume);

        saveInventory(bookInventory);
        logger.info("Book registration successful for title: '{}', author: '{}'", title, author);
    }

    /**
     * Checks the input parameters for book registration and throws exceptions if validation fails.
     *
     * @param title   The title of the book.
     * @param author  The author of the book.
     * @param genre   The genre of the book.
     * @param year    The publication year of the book.
     * @param amount  The quantity of the book.
     * @param resume  The summary or description of the book.
     * @throws IncorrectInputException If the input validation fails.
     * @throws NumberFormatException   If there is an issue parsing numeric input.
     */
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

    /**
     * Checks if the given input string contains numeric digits.
     *
     * @param input The input string to be checked.
     * @return {@code true} if the input contains numeric digits, {@code false} otherwise.
     */
    private boolean containsNumbers(String input) {
        return input.matches(".*\\d+.*");
    }

    /**
     * Gets the quantity from the provided amount string. Returns 1 if the string is empty.
     *
     * @param amount The string representing the quantity.
     * @return The parsed quantity or 1 if the string is empty.
     */
    private int getQuantity(String amount) {
        if (amount.isEmpty())
            return 1;
        else
            return Integer.parseInt(amount);
    }

    /**
     * Creates a new {@link com.library.database.entities.BookInventory BookInventory} instance with the specified details
     * and saves it to the repository. Manages the association between books and the representative book.
     *
     * @param title   The title of the book.
     * @param author  The author of the book.
     * @param genre   The genre of the book.
     * @param quantity The quantity of the book.
     * @param year    The publication year of the book.
     * @param resume  The summary or description of the book.
     * @return The created BookInventory instance.
     */
    private BookInventory createBookInventory(String title, String author, Genre genre, int quantity, String year, String resume) {
        BookInventoryRepository bookInventoryRepository = BookInventoryRepository.getInstance();
        BookInventory bookInventory = BookInventory.builder().build();

        Book representativeBook = createBook(title, author, genre, year, resume);

        boolean flag = true;

        for (BookInventory existingInventory : bookInventoryRepository.findAll()) {
            if (existingInventory.getRepresentativeBook().equalsBook(representativeBook)) {
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
                bookInventory.setRepresentativeBook(book);
                book.setInventory(bookInventory);

                saveBook(book);

                bookList.add(book);
            }

            bookInventory.setBookList(bookList);
        }

        return bookInventory;
    }

    /**
     * Creates a new {@link com.library.database.entities.Book Book} instance with the specified details.
     * Associates the book with an author and handles parsing of the publication year.
     *
     * @param title   The title of the book.
     * @param author  The author of the book.
     * @param genre   The genre of the book.
     * @param year    The publication year of the book.
     * @param resume  The summary or description of the book.
     * @return The created Book instance.
     */
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

    /**
     * Saves the specified {@link com.library.database.entities.BookInventory BookInventory} to the repository.
     *
     * @param bookInventory The BookInventory instance to be saved.
     */
    public void saveInventory(BookInventory bookInventory) {
        performRepositoryOperation(() -> bookInventoryRepository.save(bookInventory), "saved", "BookInventory");
    }

    /**
     * Performs a repository operation specified by the provided {@code repositoryOperation} supplier.
     * Logs the result and status using the logger.
     *
     * @param repositoryOperation The supplier representing the repository operation to be performed.
     * @param action             A string describing the action performed (e.g., "saved", "deleted").
     * @param entityName         A string representing the entity type on which the operation is performed.
     * @param <T>                The type of the result of the repository operation.
     */
    private <T> void performRepositoryOperation(Supplier<T> repositoryOperation, String action, String entityName) {
        T result = repositoryOperation.get();
        if (result != null) {
            logger.info("{} {} successfully: {}", entityName, action, entityName);
        } else {
            logger.error("Failed to {} {}: {}", action, entityName, entityName);
        }
    }

    /**
     * Saves the specified {@link com.library.database.entities.Book Book} to the repository.
     *
     * @param book The Book instance to be saved.
     */
    public void saveBook(Book book) {
        performRepositoryOperation(() -> bookRepository.save(book), "saved", "Book");
    }
}
