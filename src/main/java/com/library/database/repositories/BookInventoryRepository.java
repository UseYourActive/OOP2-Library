package com.library.database.repositories;

import com.library.database.entities.BookInventory;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BookInventoryRepository} class provides specific repository operations for managing {@link BookInventory} entities
 * using Hibernate as the underlying ORM (Object-Relational Mapping) framework. It extends the generic {@link Repository}
 * class and implements methods for finding book inventories by various criteria.
 *
 * <p>This class follows the Singleton design pattern to ensure a single instance is used across the application,
 * promoting consistency in entity management.</p>
 *
 * <p>Usage of this class includes finding book inventories by ID, retrieving all book inventories, getting book
 * inventories by ID.</p>
 *
 * <p>Note: This class is thread-safe due to the Singleton pattern, making it suitable for use in multi-threaded
 * environments.</p>
 *
 * @see BookInventory
 * @see Repository
 * @see HibernateException
 * @see Logger
 */
public class BookInventoryRepository extends Repository<BookInventory> {
    private static final Logger logger = LoggerFactory.getLogger(BookInventoryRepository.class);
    private static volatile BookInventoryRepository instance;

    private BookInventoryRepository() {
    }

    /**
     * Gets the singleton instance of the {@code BookInventoryRepository}.
     *
     * @return The singleton instance of the {@code BookInventoryRepository}.
     */
    public static BookInventoryRepository getInstance() {
        if (instance == null) {
            synchronized (BookInventoryRepository.class) {
                if (instance == null) {
                    instance = new BookInventoryRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Find and return a book inventory by its unique identifier (ID). This method utilizes the Hibernate session to perform
     * the database operation. If the book inventory is not found, an empty {@code Optional} is returned.
     *
     * @param id The ID of the book inventory to find.
     * @return An {@code Optional} containing the found book inventory, or empty if the book inventory is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Optional<BookInventory> findById(Long id) throws HibernateException {
        try {
            BookInventory bookInventory = session.get(BookInventory.class, id);
            if (bookInventory != null) {
                logger.info("Successfully found BookInventory with id: {}", id);
            } else {
                logger.info("No BookInventory found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(bookInventory);
        } catch (HibernateException e) {
            logger.error("Error finding BookInventory by ID: {}", id, e);
            throw e;
        }
    }

    /**
     * Retrieve and return a list of all book inventories. This method uses Hibernates HQL (Hibernate Query Language) to execute
     * a query to fetch all book inventories.
     *
     * @return A list containing all book inventories.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public List<BookInventory> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT b FROM BookInventory b", BookInventory.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all books", e);
            throw e;
        }
    }

    /**
     * Get and return a book inventory by its unique identifier (ID). This method is similar to {@link #findById(Long)}, but
     * it returns the book inventory directly instead of wrapping it in an {@code Optional}. If the book inventory is not found,
     * it returns null.
     *
     * @param id The ID of the book inventory to get.
     * @return The found book inventory, or null if the book inventory is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public BookInventory getById(Long id) throws HibernateException {
        BookInventory bookInventory = session.get(BookInventory.class, id);
        if (bookInventory != null) {
            logger.info("Successfully found book with id: {}", id);
        }
        return bookInventory;
    }
}
