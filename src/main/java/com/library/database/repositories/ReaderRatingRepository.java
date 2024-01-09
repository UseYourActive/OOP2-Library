package com.library.database.repositories;

import com.library.database.entities.ReaderRating;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ReaderRatingRepository} class provides methods for interacting with the database to perform CRUD (Create, Read,
 * Update, Delete) operations on {@code ReaderRating} entities. It extends the {@code Repository} class and inherits common
 * functionality for managing Hibernate sessions and transactions.
 *
 * <p>This class follows the Singleton pattern to ensure a single instance is used throughout the application. The
 * {@code getInstance()} method provides access to the singleton instance.</p>
 *
 * <p>Concrete implementations include methods to find a reader rating by ID, retrieve all reader ratings, get a reader
 * rating by ID, and find all reader ratings.</p>
 *
 * <p>Usage of this class requires proper exception handling, especially for Hibernate-related exceptions that may occur
 * during database interactions.</p>
 *
 * <p>Note: This class is thread-safe and can be safely accessed by multiple threads concurrently.</p>
 *
 * @see ReaderRating
 * @see Repository
 * @see HibernateException
 * @see Optional
 * @see Logger
 */
public class ReaderRatingRepository extends Repository<ReaderRating> {
    private static final Logger logger = LoggerFactory.getLogger(ReaderRatingRepository.class);
    private static volatile ReaderRatingRepository instance;

    private ReaderRatingRepository() {
    }

    /**
     * Gets the singleton instance of the {@code ReaderRatingRepository}.
     *
     * @return The singleton instance of the {@code ReaderRatingRepository}.
     */
    public static ReaderRatingRepository getInstance() {
        if (instance == null) {
            synchronized (ReaderRatingRepository.class) {
                if (instance == null) {
                    instance = new ReaderRatingRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Find and return a {@code ReaderRating} entity by its unique identifier (ID). If the reader rating is not found,
     * an empty {@code Optional} is returned.
     *
     * @param id The ID of the reader rating to find.
     * @return An {@code Optional} containing the found reader rating, or empty if the reader rating is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Optional<ReaderRating> findById(Long id) throws HibernateException {
        try {
            ReaderRating readerRating = session.get(ReaderRating.class, id);
            if (readerRating != null) {
                logger.info("Successfully found reader rating with id: {}", id);
            } else {
                logger.info("No reader rating found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(readerRating);
        } catch (HibernateException e) {
            logger.error("Error finding reader rating by ID: {}", id, e);
            throw e;
        }
    }

    /**
     * Retrieve and return a list of all {@code ReaderRating} entities. This method uses HQL (Hibernate Query Language) to
     * execute a query to fetch all reader ratings.
     *
     * @return A list containing all reader rating entities.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public List<ReaderRating> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT r FROM ReaderRating r", ReaderRating.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all reader ratings", e);
            throw e;
        }
    }

    /**
     * Get and return a {@code ReaderRating} entity by its unique identifier (ID). If the reader rating is not found, it
     * returns null.
     *
     * @param id The ID of the reader rating to get.
     * @return The found reader rating, or null if the reader rating is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public ReaderRating getById(Long id) throws HibernateException {
        ReaderRating readerRating = session.get(ReaderRating.class, id);
        if (readerRating != null) {
            logger.info("Successfully found reader rating with id: {}", id);
        }
        return readerRating;
    }
}
