package com.library.database.repositories;

import com.library.database.entities.Book;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The {@code BookRepository} class provides specific repository operations for managing {@link Book} entities
 * using Hibernate as the underlying ORM (Object-Relational Mapping) framework. It extends the generic {@link Repository}
 * class and implements methods for finding books by various criteria.
 *
 * <p>This class follows the Singleton design pattern to ensure a single instance is used across the application,
 * promoting consistency in entity management.</p>
 *
 * <p>Usage of this class includes finding books by ID, retrieving all books, getting books by ID, deleting multiple books,
 * finding books by ISBN, title, and inventory number.</p>
 *
 * <p>Note: This class is thread-safe due to the Singleton pattern, making it suitable for use in multi-threaded
 * environments.</p>
 *
 * @see Book
 * @see Repository
 * @see HibernateException
 * @see Logger
 */
public class BookRepository extends Repository<Book> {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    private static volatile BookRepository instance;

    private BookRepository() {
    }

    /**
     * Gets the singleton instance of the {@code BookRepository}.
     *
     * @return The singleton instance of the {@code BookRepository}.
     */
    public static BookRepository getInstance() {
        if (instance == null) {
            synchronized (BookRepository.class) {
                if (instance == null) {
                    instance = new BookRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Find and return a book by its unique identifier (ID). This method utilizes the Hibernate session to perform
     * the database operation. If the book is not found, an empty {@code Optional} is returned.
     *
     * @param id The ID of the book to find.
     * @return An {@code Optional} containing the found book, or empty if the book is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Optional<Book> findById(Long id) throws HibernateException {
        try {
            Book book = session.get(Book.class, id);
            if (book != null) {
                logger.info("Successfully found book with id: {}", id);
            } else {
                logger.info("No book found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(book);
        } catch (HibernateException e) {
            logger.error("Error finding book by ID: {}", id, e);
            throw e;
        }
    }

    public void saveAll(Collection<Book> entities) throws HibernateException {
        actionInsideOfTransaction(session -> {
            for (Book entity : entities) {
                session.persist(entity);
                logger.info("Entity with ID {} saved successfully", entity.getId());
            }
        });
    }

    /**
     * Delete multiple books from the database. This method encapsulates the deletion operation inside a Hibernate
     * transaction.
     *
     * @param entities The collection of books to be deleted.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public void deleteAll(Collection<Book> entities) throws HibernateException {
        actionInsideOfTransaction(session -> {
            for (Book entity : entities) {
                session.remove(entity);
                logger.info("Entity with ID {} deleted successfully", entity.getId());
            }
        });
    }

    /**
     * Retrieve and return a list of all books. This method uses Hibernates HQL (Hibernate Query Language) to execute
     * a query to fetch all books.
     *
     * @return A list containing all books.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public List<Book> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all books", e);
            throw e;
        }
    }

    /**
     * Get and return a book by its unique identifier (ID). This method is similar to {@link #findById(Long)}, but
     * it returns the book directly instead of wrapping it in an {@code Optional}. If the book is not found, it returns null.
     *
     * @param id The ID of the book to get.
     * @return The found book, or null if the book is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Book getById(Long id) {
        Book book = session.get(Book.class, id);
        if (book != null) {
            logger.info("Successfully found book with id: {}", id);
        }
        return book;
    }

    /**
     * Execute a Hibernate query with parameters and return the result as an Optional.
     *
     * <p>This method is a generic utility for executing Hibernate queries with parameters and handling the results.
     * It takes a HQL (Hibernate Query Language) query, parameter name, parameter value, and the expected result type
     * as input and returns an {@code Optional} containing the result or an empty {@code Optional} if no result is found.</p>
     *
     * <p>The method utilizes the Hibernate session to execute the query and sets the specified parameter values.
     * It catches {@code NoResultException} to log an informational message when no result is found, and it catches
     * {@code HibernateException} to log an error message and rethrow the exception.</p>
     *
     * @param query The HQL query to execute.
     * @param paramName The name of the parameter in the query.
     * @param paramValue The value of the parameter in the query.
     * @param resultType The expected result type of the query.
     * @param <T> The generic type representing the result.
     * @return An {@code Optional} containing the result of the query, or empty if no result is found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     * @see Optional
     * @see NoResultException
     */
    private <T> Optional<T> executeQuery(String query, String paramName, String paramValue, Class<T> resultType) throws HibernateException {
        try {
            return Optional.ofNullable(
                    session.createQuery(query, resultType)
                            .setParameter(paramName, paramValue)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            logger.info("No result found with {} : {}", paramName, paramValue);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error executing query: {}", query, e);
            throw e;
        }
    }

    /**
     * Execute a query to find a book by its ISBN (International Standard Book Number).
     *
     * @param isbn The ISBN of the book to find.
     * @return An {@code Optional} containing the found book by ISBN, or empty if the book is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public Optional<Book> findByIsbn(String isbn) throws HibernateException {
        String query = "SELECT b FROM Book b WHERE b.isbn = :isbn";
        return executeQuery(query, "isbn", isbn, Book.class);
    }

    /**
     * Execute a query to find a book by its title.
     *
     * @param title The title of the book to find.
     * @return An {@code Optional} containing the found book by title, or empty if the book is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public Optional<Book> findByTitle(String title) throws HibernateException {
        String query = "SELECT b FROM Book b WHERE b.title = :title";
        return executeQuery(query, "title", title, Book.class);
    }

    /**
     * Execute a query to find a book by its inventory number.
     *
     * @param inventoryNumber The inventory number of the book to find.
     * @return An {@code Optional} containing the found book by inventory number, or empty if the book is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public Optional<Book> findByInventoryNumber(String inventoryNumber) throws HibernateException {
        String query = "SELECT b FROM Book b WHERE b.inventoryNumber = :inventoryNumber";
        return executeQuery(query, "inventoryNumber", inventoryNumber, Book.class);
    }
}
