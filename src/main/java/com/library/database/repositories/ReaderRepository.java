package com.library.database.repositories;

import com.library.database.entities.Reader;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ReaderRepository} class provides methods for interacting with the database to perform CRUD (Create, Read,
 * Update, Delete) operations on {@code Reader} entities. It extends the {@code Repository} class and inherits common
 * functionality for managing Hibernate sessions and transactions.
 *
 * <p>This class follows the Singleton pattern to ensure a single instance is used throughout the application. The
 * {@code getInstance()} method provides access to the singleton instance.</p>
 *
 * <p>Concrete implementations include methods to find a reader by ID, retrieve all readers, get a reader by ID, find a
 * reader by email, and find a reader by phone number. These methods utilize Hibernate for database interactions.</p>
 *
 * <p>The class also includes a utility method, {@link #executeQuery(String, String, String, Class)}, to execute a
 * Hibernate query with parameters and handle the result as an {@code Optional}.</p>
 *
 * <p>Usage of this class requires proper exception handling, especially for Hibernate-related exceptions that may occur
 * during database interactions.</p>
 *
 * <p>Note: This class is thread-safe and can be safely accessed by multiple threads concurrently.</p>
 *
 * @see Reader
 * @see Repository
 * @see HibernateException
 * @see Optional
 * @see NoResultException
 */
public class ReaderRepository extends Repository<Reader> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderRepository.class);
    private static volatile ReaderRepository instance;

    private ReaderRepository() {
    }

    /**
     * Get the singleton instance of the {@code ReaderRepository}. If the instance does not exist, it is created in a
     * thread-safe manner using double-checked locking.
     *
     * @return The singleton instance of the {@code ReaderRepository}.
     */
    public static ReaderRepository getInstance() {
        if (instance == null) {
            synchronized (ReaderRepository.class) {
                if (instance == null) {
                    instance = new ReaderRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Find and return a {@code Reader} entity by its unique identifier (ID). If the reader is not found, an empty
     * {@code Optional} is returned.
     *
     * @param id The ID of the reader to find.
     * @return An {@code Optional} containing the found reader, or empty if the reader is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Optional<Reader> findById(Long id) throws HibernateException {
        try {
            Reader reader = session.get(Reader.class, id);
            if (reader != null) {
                logger.info("Successfully found reader with id: {}", id);
            } else {
                logger.info("No reader found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(reader);
        } catch (HibernateException e) {
            logger.error("Error finding reader by ID: {}", id, e);
            throw e;
        }
    }

    /**
     * Retrieve and return a list of all {@code Reader} entities. This method uses HQL (Hibernate Query Language) to execute
     * a query to fetch all readers.
     *
     * @return A list containing all reader entities.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public List<Reader> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT r FROM Reader r", Reader.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all readers", e);
            throw e;
        }
    }

    /**
     * Get and return a {@code Reader} entity by its unique identifier (ID). If the reader is not found, it returns null.
     *
     * @param id The ID of the reader to get.
     * @return The found reader, or null if the reader is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Reader getById(Long id) throws HibernateException {
        Reader reader = session.get(Reader.class, id);
        if (reader != null) {
            logger.info("Successfully found reader with id: {}", id);
        }
        return reader;
    }

    /**
     * Execute a Hibernate query with parameters and handle the result as an {@code Optional}. If the query returns no
     * result, an empty {@code Optional} is returned.
     *
     * @param query The HQL query to execute.
     * @param paramName The name of the parameter in the query.
     * @param paramValue The value of the parameter in the query.
     * @param resultType The expected result type of the query.
     * @param <T> The type of the result.
     * @return An {@code Optional} containing the result of the query, or empty if no result is found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
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
     * Find and return a {@code Reader} entity by its email address. If the reader is not found, an empty {@code Optional} is
     * returned.
     *
     * @param email The email address of the reader to find.
     * @return An {@code Optional} containing the found reader, or empty if the reader is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public Optional<Reader> findByEmail(String email) throws HibernateException {
        String query = "SELECT r FROM Reader r WHERE r.email = :email";
        return executeQuery(query, "email", email, Reader.class);
    }

    /**
     * Find and return a {@code Reader} entity by its phone number. If the reader is not found, an empty {@code Optional} is
     * returned.
     *
     * @param phoneNumber The phone number of the reader to find.
     * @return An {@code Optional} containing the found reader, or empty if the reader is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public Optional<Reader> findByPhoneNumber(String phoneNumber) throws HibernateException {
        String query = "SELECT r FROM Reader r WHERE r.phone_number = :phone_number";
        return executeQuery(query, "phone_number", phoneNumber, Reader.class);
    }
}
