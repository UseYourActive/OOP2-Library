package com.library.database.repositories;

import com.library.database.entities.BookForm;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BookFormRepository extends Repository<BookForm> {
    private static final Logger logger = LoggerFactory.getLogger(BookFormRepository.class);
    private static volatile BookFormRepository instance;

    private BookFormRepository() {
    }

    public static BookFormRepository getInstance() {
        if (instance == null) {
            synchronized (BookFormRepository.class) {
                if (instance == null) {
                    instance = new BookFormRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<BookForm> findById(Long id) throws HibernateException {
        try {
            BookForm bookRequestForm = session.get(BookForm.class, id);
            if (bookRequestForm != null) {
                logger.info("Successfully found book request form with ID: {}", id);
            } else {
                logger.info("No book request form found with ID: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(bookRequestForm);
        } catch (HibernateException e) {
            logger.error("Error finding book request form by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<BookForm> findAll() throws HibernateException {
        try {
            logger.info("Finding all book request forms");
            List<BookForm> bookRequestForms = session.createQuery("SELECT b FROM BookForm b", BookForm.class).getResultList();
            logger.info("Found {} book request forms", bookRequestForms.size());
            return bookRequestForms;
        } catch (HibernateException e) {
            logger.error("Error retrieving all book request forms", e);
            throw e;
        }
    }

    @Override
    public BookForm getById(Long id) throws HibernateException {
        try {
            logger.info("Successfully found book request form with ID: {}", id);
            return session.get(BookForm.class, id);
        } catch (HibernateException e) {
            logger.error("Error getting book request form by ID: {}", id, e);
            throw e;
        }
    }

    public Optional<BookForm> findGenreByName(String name) throws HibernateException {
        try {
            return session.createQuery("SELECT b FROM BookForm b WHERE b.name = :name", BookForm.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst();
        } catch (HibernateException e) {
            logger.error("Error finding book request form by name: {}", name, e);
            throw e;
        }
    }
}
