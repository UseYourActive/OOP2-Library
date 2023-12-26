package com.library.backend.services;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.entities.Reader;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperatorService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    private final BookInventoryRepository bookInventoryRepository;

    public OperatorService(BookRepository bookRepository, ReaderRepository readerRepository,BookInventoryRepository bookInventoryRepository) {
        this.bookRepository = Preconditions.checkNotNull(bookRepository, "BookRepository cannot be null");
        this.readerRepository = Preconditions.checkNotNull(readerRepository, "ReaderRepository cannot be null");
        this.bookInventoryRepository=Preconditions.checkNotNull(bookInventoryRepository,"BookInventoryRepository cannot be null");
    }

    public void lendBook(Book book) {
        updateBookStatus(book, BookStatus.LENT, "lent");
    }

    public void archiveBook(Book book) {
        updateBookStatus(book, BookStatus.ARCHIVED, "archived");
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        //logEntityRetrieval("book_inventories", inventories.size());
        return inventories;
    }

    public void createReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.save(reader);
    }

    public void removeReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.delete(reader);
    }

    private void updateBookStatus(Book book, BookStatus newStatus, String action) {
        Preconditions.checkNotNull(book, "Book cannot be null");
        book.setBookStatus(newStatus);
        boolean result = bookRepository.save(book);
        if (result) {
            logger.info("Book {} successfully: {}", action, book.getTitle());
        } else {
            logger.error("Failed to {} book: {}", action, book.getTitle());
        }
    }

    private void logReaders(List<Reader> readers) {
        logger.info("Readers: {}", Joiner.on(", ").join(readers));
    }
}
