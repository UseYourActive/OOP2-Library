package com.library.backend.services.admin;

import com.library.backend.exception.InvalidQuantityException;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddBookQuantityControllerService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(AddBookQuantityControllerService.class);
    private final BookRepository bookRepository;
    private final BookInventoryRepository bookInventoryRepository;

    public AddBookQuantityControllerService(BookRepository bookRepository, BookInventoryRepository bookInventoryRepository) {
        this.bookRepository = bookRepository;
        this.bookInventoryRepository = bookInventoryRepository;
    }

    public void increaseBookQuantity(String quantityString, BookInventory bookInventory) throws NumberFormatException, InvalidQuantityException {
        try {
            int quantity = Integer.parseInt(quantityString);

            if (quantity < 0) {
                logger.error("Invalid quantity: {}", quantityString);
                throw new InvalidQuantityException("Invalid quantity");
            }

            Book representiveBook = bookInventory.getRepresentiveBook();

            for (int i = 0; i < quantity; i++) {
                Book book = new Book(representiveBook);
                book.setBookStatus(BookStatus.AVAILABLE);
                book.setNumberOfTimesUsed(0);

                bookRepository.save(book);
                bookInventory.addBook(book);
            }

            bookInventoryRepository.update(bookInventory);
            logger.info("Increased book quantity successfully for book: '{}', quantity: {}", representiveBook.getTitle(), quantity);
        } catch (InvalidQuantityException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}
