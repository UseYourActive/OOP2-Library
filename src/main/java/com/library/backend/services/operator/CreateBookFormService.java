package com.library.backend.services.operator;

import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.ReaderException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.Service;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import com.library.database.enums.Ratings;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateBookFormService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(CreateBookFormService.class);
    private final ReaderRepository readerRepository;
    private final BookFormRepository bookFormRepository;
    @Getter private final BookRepository bookRepository;
    @Getter
    @Setter
    private SearchEngine<Reader> readerSearchEngine;

    @Setter
    @Getter
    private double ratingValue;

    public CreateBookFormService(ReaderRepository readerRepository, BookFormRepository bookFormRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.bookFormRepository = bookFormRepository;
        this.bookRepository = bookRepository;
        this.readerSearchEngine = new ReaderSearchEngine();
    }

    public List<Reader> getAllReaders() {
        List<Reader> readers = readerRepository.findAll();
        logger.info("Retrieved {} readers from the repository.", readers.size());
        return readers;
    }

    public Collection<Reader> searchReader(String stringToSearch) throws SearchEngineException {
        logger.info("Searching for readers with '{}'", stringToSearch);
        return readerSearchEngine.search(readerRepository.findAll(), stringToSearch);
    }

    public void lendBooks(Reader selectedReader, List<Book> bookList) throws ReaderException {
        logger.info("Lending books to reader '{}'", selectedReader.getFullName());

        // Null check for ReaderRating
        if (selectedReader.getReaderRating() == null) {
            logger.warn("The reader '{}' does not have a rating.", selectedReader.getFullName());
            throw new ReaderException("The reader does not have a rating.");
        }

        if (selectedReader.getReaderRating().getRating() == Ratings.ZERO_STAR) {
            logger.warn("The reader '{}' is not allowed to take books anymore.", selectedReader.getFullName());
            throw new ReaderException("The reader is not allowed to take books anymore.");
        }

        if (bookList.stream().allMatch(book -> book.getBookStatus().equals(BookStatus.AVAILABLE))) {
            logger.info("Changing status of books to 'LENT'");
            changeBookStatus(bookList, BookStatus.LENT);

            BookForm bookForm = BookForm.builder()
                    .reader(selectedReader)
                    .books(bookList)
                    .status(BookFormStatus.IN_USE)
                    .expirationDate(LocalDateTime.now().plusMonths(1))
                    .dateOfCreation(LocalDateTime.now())
                    .build();

            bookFormRepository.save(bookForm);
            logger.info("Saved new book form for reader '{}'", selectedReader.getFullName());
            //operatorService.saveNewBookForm(bookForm);

            selectedReader.getBookForms().add(bookForm);

            readerRepository.save(selectedReader);
            logger.info("Updated reader information for '{}'", selectedReader.getFullName());
            //operatorService.saveReader(selectedReader);
        } else {
            logger.warn("For normal lending, the reader '{}' can take only AVAILABLE books.", selectedReader.getFullName());
            throw new ReaderException("For normal lending, the reader can take only AVAILABLE books.");
        }
    }


    public void lendReadingRoomBooks(Reader selectedReader, List<Book> bookList) throws ReaderException {
        logger.info("Lending reading room books to reader '{}'", selectedReader.getFullName());

        if (selectedReader.getReaderRating().getRating() == Ratings.ZERO_STAR) {
            logger.warn("Reader '{}' is not allowed to take books anymore. His rating is too low.", selectedReader.getFullName());
            throw new ReaderException("Reader is not allowed to take books anymore. His rating is too low.");
        }

        logger.info("Changing status of books to 'IN_READING_ROOM'");
        changeBookStatus(bookList, BookStatus.IN_READING_ROOM);

        BookForm bookForm = BookForm.builder()
                .reader(selectedReader)
                .books(bookList)
                .status(BookFormStatus.IN_USE)
                .expirationDate(LocalDateTime.now().plusHours(12))
                .dateOfCreation(LocalDateTime.now())
                .build();

        bookFormRepository.save(bookForm);
        logger.info("Saved new book form for reader '{}'", selectedReader.getFullName());
        //operatorService.saveNewBookForm(bookForm);

        selectedReader.getBookForms().add(bookForm);

        readerRepository.save(selectedReader);
        logger.info("Updated reader information for '{}'", selectedReader.getFullName());
        //operatorService.saveReader(selectedReader);
    }

    private void changeBookStatus(Collection<Book> books, BookStatus status) {
        logger.info("Changing book status to '{}' for {} books", status, books.size());

        Set<Book> bookSet = new HashSet<>();
        for (Book book : books) {
            book.updatePreviousBookStatus();
            book.setBookStatus(status);
            bookSet.add(book);
        }
        bookRepository.saveAll(bookSet);
        logger.info("Book status changed successfully");
    }
}
