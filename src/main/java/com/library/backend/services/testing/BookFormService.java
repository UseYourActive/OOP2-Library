package com.library.backend.services.testing;

import com.google.common.base.Preconditions;
import com.library.backend.services.Service;
import com.library.database.entities.BookForm;
import com.library.database.repositories.BookFormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BookFormService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(BookFormService.class);
    private final BookFormRepository bookFormRepository;

    public BookFormService(BookFormRepository bookFormRepository) {
        this.bookFormRepository = Preconditions.checkNotNull(bookFormRepository, "BookFormRepository cannot be null");
    }

    public void removeAllBookForms(List<BookForm> bookForms) {
        bookFormRepository.deleteAll(bookForms);
    }

    public List<BookForm> getAllBookForms() {
        List<BookForm> bookForms = bookFormRepository.findAll();
        logger.info("Retrieved {} books from the repository.", bookForms.size());
        return bookForms;
    }

    public void saveNewBookForm(BookForm bookForm) {
        Preconditions.checkNotNull(bookForm, "Book form cannot be null");
        bookFormRepository.save(bookForm);
        logger.info("Created new book form: {}", bookForm);
    }
}
