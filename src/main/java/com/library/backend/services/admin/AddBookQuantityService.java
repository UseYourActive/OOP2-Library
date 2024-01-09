package com.library.backend.services.admin;

import com.library.backend.exception.InvalidQuantityException;
import com.library.backend.exception.ObjectCannotBeNullException;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code AddBookQuantityService} class provides functionality to increase the quantity of books
 * associated with a given {@link com.library.database.entities.BookInventory BookInventory}.
 * It ensures valid quantity input and handles the creation and addition of new books to the inventory.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create an AddBookQuantityService instance with BookRepository and BookInventoryRepository
 * AddBookQuantityService addBookQuantityService = new AddBookQuantityService(BookRepository.getInstance(), BookInventoryRepository.getInstance());
 *
 * // Increase the quantity of books in a BookInventory
 * try {
 *     BookInventory bookInventory = // obtain a BookInventory instance;
 *     addBookQuantityService.increaseBookQuantity("5", bookInventory);
 *     // Book quantity increased successfully
 * } catch (NumberFormatException | InvalidQuantityException | ObjectCannotBeNullException e) {
 *     // Handle the case where increasing the book quantity fails
 * }
 * }
 * </pre>
 * In this example, an {@code AddBookQuantityService} instance is created with the necessary repositories,
 * and the {@code increaseBookQuantity} method is used to add a specified quantity of books to a given BookInventory.
 * If the book quantity is increased successfully, the log will indicate success; otherwise, an exception is thrown
 * with details about the failure.
 * <p>
 * The {@code AddBookQuantityService} class implements the {@link com.library.backend.services.Service Service}
 * interface, providing a common interface for various services in the application.
 *
 * @see com.library.backend.services.Service
 * @see com.library.database.entities.Book
 * @see com.library.database.entities.BookInventory
 * @see com.library.database.repositories.BookRepository
 * @see com.library.database.repositories.BookInventoryRepository
 * @see com.library.backend.exception.InvalidQuantityException
 * @see com.library.backend.exception.ObjectCannotBeNullException
 */
public class AddBookQuantityService implements Service {

    private final static Logger logger = LoggerFactory.getLogger(AddBookQuantityService.class);

    private final BookRepository bookRepository;
    private final BookInventoryRepository bookInventoryRepository;

    /**
     * Constructs an {@code AddBookQuantityService} instance with the specified repositories.
     *
     * @param bookRepository          The repository for accessing book data.
     * @param bookInventoryRepository The repository for accessing book inventory data.
     */
    public AddBookQuantityService(BookRepository bookRepository, BookInventoryRepository bookInventoryRepository) {
        this.bookRepository = bookRepository;
        this.bookInventoryRepository = bookInventoryRepository;
    }

    /**
     * Increases the quantity of books in the provided {@link com.library.database.entities.BookInventory BookInventory}.
     *
     * @param quantityString The string representing the quantity to increase.
     * @param bookInventory  The BookInventory instance to which books will be added.
     * @throws NumberFormatException      If the provided quantity string cannot be parsed into an integer.
     * @throws InvalidQuantityException   If the provided quantity is invalid (non-positive).
     * @throws ObjectCannotBeNullException If the provided BookInventory is null.
     */
    public void increaseBookQuantity(String quantityString, BookInventory bookInventory) throws NumberFormatException, InvalidQuantityException, ObjectCannotBeNullException {
        try {
            if (bookInventory == null) {
                throw new ObjectCannotBeNullException("BookInventory cannot be null!");
            }

            int quantity = Integer.parseInt(quantityString);

            if (quantity <= 0) {
                logger.error("Invalid quantity: {}", quantityString);
                throw new InvalidQuantityException();
            }

            Book representiveBook = bookInventory.getRepresentativeBook();

            for (int i = 0; i < quantity; i++) {
                Book book = new Book(representiveBook);
                book.setBookStatus(BookStatus.AVAILABLE);
                book.setNumberOfTimesUsed(0);

                bookRepository.save(book);
                bookInventory.addBook(book);
            }

            bookInventoryRepository.update(bookInventory);
            logger.info("Increased book quantity successfully for book: '{}', quantity: {}", representiveBook.getTitle(), quantity);
        } catch (InvalidQuantityException | NumberFormatException | ObjectCannotBeNullException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
