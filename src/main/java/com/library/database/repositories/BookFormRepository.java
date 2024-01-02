package com.library.database.repositories;

import com.library.database.entities.BookForm;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BookFormRepository} class provides specific repository operations for managing {@link BookForm} entities
 * using Hibernate as the underlying ORM (Object-Relational Mapping) framework. It extends the generic {@link Repository}
 * class and implements methods for finding book request forms by various criteria.
 *
 * <p>This class follows the Singleton design pattern to ensure a single instance is used across the application,
 * promoting consistency in entity management.</p>
 *
 * <p>Usage of this class includes finding book request forms by ID, retrieving all book request forms, getting book
 * request forms by ID, and finding book request forms by name.</p>
 *
 * <p>Note: This class is thread-safe due to the Singleton pattern, making it suitable for use in multi-threaded
 * environments.</p>
 *
 * @see BookForm
 * @see Repository
 * @see HibernateException
 * @see Logger
 */
public class BookFormRepository extends Repository<BookForm> {
    private static final Logger logger = LoggerFactory.getLogger(BookFormRepository.class);
    private static volatile BookFormRepository instance;

    private BookFormRepository() {
    }

    /**
     * Gets the singleton instance of the {@code BookFormRepository}.
     *
     * @return The singleton instance of the {@code BookFormRepository}.
     */
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

    /**
     * Find and return a book request form by its unique identifier (ID). This method utilizes the Hibernate session to perform
     * the database operation. If the book request form is not found, an empty {@code Optional} is returned.
     *
     * @param id The ID of the book request form to find.
     * @return An {@code Optional} containing the found book request form, or empty if the book request form is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Retrieve and return a list of all book request forms. This method uses Hibernates HQL (Hibernate Query Language) to execute
     * a query to fetch all book request forms.
     *
     * @return A list containing all book request forms.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Get and return a book request form by its unique identifier (ID). This method is similar to {@link #findById(Long)}, but
     * it returns the book request form directly instead of wrapping it in an {@code Optional}. If the book request form is not found,
     * it returns null.
     *
     * @param id The ID of the book request form to get.
     * @return The found book request form, or null if the book request form is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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

    /**
     * Find and return a book request form by its name. This method uses a parameterized query to search for a book request form with the
     * specified name. If the book request form is not found, an empty {@code Optional} is returned.
     *
     * @param name The name of the book request form to find.
     * @return An {@code Optional} containing the found book request form, or empty if the book request form is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
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
