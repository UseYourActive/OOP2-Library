package com.library.backend.services;

import com.library.database.entities.Book;
import com.library.database.entities.Reader;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperatorService implements Service {
    private final Logger logger = LoggerFactory.getLogger(OperatorService.class);
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    public OperatorService(BookRepository bookRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public void archiveBook(Book book) {
        book.setBookStatus(BookStatus.ARCHIVED);
        boolean result = bookRepository.save(book);
        if (result) {
            logger.info("Book archived: {}", book.getTitle());
        } else {
            logger.error("Failed to archive book: {}", book.getTitle());
        }
    }

    public List<Reader> getAllReaders(){
        return readerRepository.findAll();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void createReader(Reader reader) {
        readerRepository.save(reader);
    }

    public void removeReader(Reader reader) {
        readerRepository.delete(reader);
    }
}
