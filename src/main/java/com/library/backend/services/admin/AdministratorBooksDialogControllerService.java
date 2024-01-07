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

public class AdministratorBooksDialogControllerService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(AdministratorBooksControllerService.class);
    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;

    public AdministratorBooksDialogControllerService(BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository) {
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
    }

    public void removeSelectedBooks(BookInventory bookInventory, List<Book> booksToRemove) {
        logger.info("Removing selected books: {}", booksToRemove);
        updateBookForms(booksToRemove);
        removeBooksAndUpdateRepresentativeBook(bookInventory, booksToRemove);
    }

    private void removeBooksAndUpdateRepresentativeBook(BookInventory bookInventory, List<Book> booksToRemove) {
        boolean flag = true;

        for (Book bookToRemove : booksToRemove) {
            if (bookInventory.getRepresentiveBook().equals(bookToRemove) && bookInventory.getBookList().size() == 1) {
                logger.info("Deleting book inventory: {}", bookInventory);
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
