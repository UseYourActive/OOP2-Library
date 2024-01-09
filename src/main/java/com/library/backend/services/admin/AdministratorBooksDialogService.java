package com.library.backend.services.admin;

import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The {@code AdministratorBooksDialogService} class provides functionality for managing book-related
 * actions within a dialog for administrators. This includes removing selected books from a book inventory,
 * updating associated book forms, and handling representative book changes.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create an AdministratorBooksDialogService instance with BookInventoryRepository and BookFormRepository
 * AdministratorBooksDialogService booksDialogService = new AdministratorBooksDialogService(BookInventoryRepository.getInstance(), BookFormRepository.getInstance());
 *
 * // Remove selected books from a BookInventory
 * BookInventory bookInventory = // obtain a BookInventory instance;
 * List<Book> booksToRemove = // obtain a list of books to remove;
 * booksDialogService.removeSelectedBooks(bookInventory, booksToRemove);
 * }
 * </pre>
 * In this example, an {@code AdministratorBooksDialogService} instance is created with the necessary repositories,
 * and the {@code removeSelectedBooks} method is used to remove selected books from a given BookInventory.
 * The associated book forms are updated, and the representative book is adjusted accordingly.
 * <p>
 * The {@code AdministratorBooksDialogService} class implements the {@link com.library.backend.services.Service Service}
 * interface, providing a common interface for various services in the application.
 *
 * @see com.library.backend.services.Service
 * @see com.library.database.entities.Book
 * @see com.library.database.entities.BookForm
 * @see com.library.database.entities.BookInventory
 * @see com.library.database.repositories.BookInventoryRepository
 * @see com.library.database.repositories.BookFormRepository
 */
public class AdministratorBooksDialogService implements Service {

    private final static Logger logger = LoggerFactory.getLogger(AdministratorBooksService.class);

    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;

    /**
     * Constructs an {@code AdministratorBooksDialogService} instance with the specified repositories.
     *
     * @param bookInventoryRepository The repository for accessing book inventory data.
     * @param bookFormRepository      The repository for accessing book form data.
     */
    public AdministratorBooksDialogService(BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
    }

    /**
     * Removes selected books from the provided {@link com.library.database.entities.BookInventory BookInventory}.
     * Updates associated {@link com.library.database.entities.BookForm BookForms} and handles changes to
     * the representative book within the inventory.
     *
     * @param bookInventory The BookInventory instance from which books will be removed.
     * @param booksToRemove The list of books to be removed.
     */
    public void removeSelectedBooks(BookInventory bookInventory, List<Book> booksToRemove) {
        logger.info("Removing selected books: {}", booksToRemove);
        updateBookForms(booksToRemove);
        removeBooksAndUpdateRepresentativeBook(bookInventory, booksToRemove);
    }

    private void removeBooksAndUpdateRepresentativeBook(BookInventory bookInventory, List<Book> booksToRemove) {
        boolean flag = true;

        for (Book bookToRemove : booksToRemove) {
            if (bookInventory.getRepresentativeBook().equals(bookToRemove) && bookInventory.getBookList().size() == 1) {
                logger.info("Deleting book inventory: {}", bookInventory);
                bookInventoryRepository.delete(bookInventory);
                flag = false;
                break;
            }

            if (bookInventory.getRepresentativeBook().equals(bookToRemove)) {
                List<Book> nonSelected = bookInventory.getBookList().stream()
                        .filter(book -> !booksToRemove.contains(book))
                        .toList();

                for (Book book : nonSelected) {
                    if (!book.equals(bookInventory.getRepresentativeBook())) {
                        bookInventory.setRepresentativeBook(book);
                        break;
                    }
                }
            }

            bookInventory.getBookList().remove(bookToRemove);
        }

        if (flag) {
            logger.info("Saving book inventory: {}", bookInventory);
            bookInventoryRepository.save(bookInventory);
        }
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
}
