package com.library.database.repositories;

import com.library.database.entities.BookRequestForm;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BookRequestFormRepository extends Repository<BookRequestForm> {
    private static final Logger logger = LoggerFactory.getLogger(BookRequestFormRepository.class);
    private static volatile BookRequestFormRepository instance;

    private BookRequestFormRepository() {
    }

    public static BookRequestFormRepository getInstance() {
        if (instance == null) {
            synchronized (BookRequestFormRepository.class) {
                if (instance == null) {
                    instance = new BookRequestFormRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Optional<BookRequestForm> findById(Long id) throws HibernateException {
        try {
            BookRequestForm bookRequestForm = session.get(BookRequestForm.class, id);
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
    public List<BookRequestForm> findAll() throws HibernateException {
        try {
            logger.info("Finding all book request forms");
            List<BookRequestForm> bookRequestForms = session.createQuery("SELECT b FROM BookRequestForm b", BookRequestForm.class).getResultList();
            logger.info("Found {} book request forms", bookRequestForms.size());
            return bookRequestForms;
        } catch (HibernateException e) {
            logger.error("Error retrieving all book request forms", e);
            throw e;
        }
    }

    @Override
    public BookRequestForm getById(Long id) throws HibernateException {
        try {
            logger.info("Successfully found book request form with ID: {}", id);
            return session.get(BookRequestForm.class, id);
        } catch (HibernateException e) {
            logger.error("Error getting book request form by ID: {}", id, e);
            throw e;
        }
    }

    public Optional<BookRequestForm> findGenreByName(String name) throws HibernateException {
        try {
            return session.createQuery("SELECT b FROM BookRequestForm b WHERE b.name = :name", BookRequestForm.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst();
        } catch (HibernateException e) {
            logger.error("Error finding book request form by name: {}", name, e);
            throw e;
        }
    }
}
