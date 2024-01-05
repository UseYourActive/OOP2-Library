package com.library.backend.services;

import com.google.common.base.Preconditions;
import com.library.database.entities.*;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OperatorService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final BookInventoryRepository bookInventoryRepository;
    private final BookFormRepository bookFormRepository;
    private final EventNotificationRepository eventNotificationRepository;

    public OperatorService(BookRepository bookRepository, ReaderRepository readerRepository, BookInventoryRepository bookInventoryRepository, BookFormRepository bookFormRepository, EventNotificationRepository eventNotificationRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.bookInventoryRepository = bookInventoryRepository;
        this.bookFormRepository = bookFormRepository;
        this.eventNotificationRepository = eventNotificationRepository;
    }

    public void lendBookToReaderForReadingRoom(Book book, Reader reader) {
        Preconditions.checkNotNull(book, "Book cannot be null");
        Preconditions.checkNotNull(reader, "Reader cannot be null");

        if (book.getBookStatus() == BookStatus.AVAILABLE) {
            book.setBookStatus(BookStatus.IN_READING_ROOM);

            boolean result = bookRepository.save(book);
            if (result) {
                logger.info("Book successfully lent to reader: {} - {}", reader.getFirstName() + " " + reader.getMiddleName() + " " + reader.getLastName(), book.getTitle());
            } else {
                logger.error("Failed to lend book to reader: {} - {}", reader.getFirstName() + " " + reader.getMiddleName() + " " + reader.getLastName(), book.getTitle());
            }
        } else {
            logger.warn("Cannot lend book to reader. Book status is not AVAILABLE: {}", book.getTitle());
        }
    }

    public List<EventNotification> getAllEventNotifications(){
        return eventNotificationRepository.findAll();
    }

    public void saveEventNotification(EventNotification eventNotification){
        eventNotificationRepository.saveNotification(eventNotification);
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        logger.info("Retrieved {} books from the repository.", books.size());
        return books;
    }


    public void saveAllBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public void removeAllBookForms(List<BookForm> bookForms) {
        bookFormRepository.deleteAll(bookForms);
    }

    public List<BookForm> getAllBookForms() {
        List<BookForm> bookForms = bookFormRepository.findAll();
        logger.info("Retrieved {} books from the repository.", bookForms.size());
        return bookForms;
    }

    public void lendBook(Book book) {
        updateBookStatus(book, BookStatus.LENT, "lent");
    }

    public void archiveBook(Book book) {
        updateBookStatus(book, BookStatus.ARCHIVED, "archived");
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

    public void saveReader(Reader reader) {
        readerRepository.save(reader);
    }

    public void changeBookStatus(Collection<Book> books, BookStatus status) {

        Set<Book> bookSet = new HashSet<>();
        for (Book book : books) {
            book.setBookStatus(status);
            bookSet.add(book);
        }
        bookRepository.saveAll(bookSet);
    }

    public void saveNewBookForm(BookForm bookForm) {
        Preconditions.checkNotNull(bookForm, "Book form cannot be null");
        bookFormRepository.save(bookForm);
        logger.info("Created new book form: {}", bookForm);
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        logger.info("Retrieved {} readers from the repository.", readers.size());
        return readers;
    }

    public void createReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.save(reader);
        logger.info("Created a new reader: {}", reader);
    }

    public void removeReader(Reader reader) {
        Preconditions.checkNotNull(reader, "Reader cannot be null");
        readerRepository.delete(reader);
        logger.info("Removed reader: {}", reader);
    }

    public List<BookInventory> getAllBookInventories() {
        List<BookInventory> inventories = bookInventoryRepository.findAll();
        logger.info("Retrieved {} book inventories from the repository.", inventories.size());
        return inventories;
    }
}
