package com.library.database.repositories;

import com.library.database.entities.DBEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * The {@code Repository} class provides a generic implementation for basic CRUD (Create, Read, Update, Delete) operations
 * using Hibernate as the underlying ORM (Object-Relational Mapping) framework. It serves as a base class for specific
 * repositories handling entities of a particular type.
 *
 * <p>This class manages Hibernate sessions, transactions, and provides methods for common database operations, such as
 * finding entities by ID, retrieving all entities, getting entities by ID, deleting entities, saving entities, and updating
 * entities.</p>
 *
 * <p>The class utilizes a {@code ThreadLocal} to manage the Hibernate {@code Session} instances for each thread, ensuring
 * thread-safety in a multi-threaded environment.</p>
 *
 * <p>Concrete subclasses must implement the following methods:</p>
 * <ul>
 *     <li>{@link #findById(Long)}: Find an entity by its ID.</li>
 *     <li>{@link #findAll()}: Retrieve all entities of the specified type.</li>
 *     <li>{@link #getById(Long)}: Get an entity by its ID.</li>
 * </ul>
 *
 * <p>Additionally, the class supports methods for deleting, saving, and updating entities, with these operations
 * encapsulated in a transaction.</p>
 *
 * <p>The class also provides a method for executing an action inside a Hibernate transaction, ensuring proper handling
 * of transactions.</p>
 *
 * <p>Usage of this class requires the implementation of specific repositories for entities, extending this class.</p>
 *
 * <p>Note: This class implements the {@code AutoCloseable} interface, allowing it to be used in try-with-resources
 * constructs, ensuring proper resource management.</p>
 *
 * @param <T> The type of entity managed by the repository.
 *
 * @see HibernateException
 * @see Session
 * @see Transaction
 * @see Configuration
 * @see LoggerFactory
 */
@Getter
@Setter
public abstract class Repository<T extends DBEntity> implements AutoCloseable {
    private static final SessionFactory sessionFactory;
    private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(Repository.class);
    protected Session session;
    protected Transaction transaction;

    protected Repository() {
        this.session = getThreadLocalSession();
    }

    static {
        try {
            Configuration configuration = new Configuration().configure("/sql/hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            logger.info("Hibernate initialized successfully");
        } catch (Throwable ex) {
            logger.error("Error initializing Hibernate: {}", ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Find and return an entity by its unique identifier (ID). This method utilizes the Hibernate session to perform
     * the database operation. If the entity is not found, an empty {@code Optional} is returned.
     *
     * @param id The ID of the entity to find.
     * @return An {@code Optional} containing the found entity, or empty if the entity is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public abstract Optional<T> findById(Long id) throws HibernateException;

    /**
     * Retrieve and return a list of all entities of the specified type. This method uses Hibernates HQL (Hibernate Query
     * Language) to execute a query to fetch all entities.
     *
     * @return A list containing all entities of the specified type.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public abstract List<T> findAll() throws HibernateException;

    /**
     * Get and return an entity by its unique identifier (ID). This method is similar to {@link #findById(Long)}, but it
     * returns the entity directly instead of wrapping it in an {@code Optional}. If the entity is not found, it returns null.
     *
     * @param id The ID of the entity to get.
     * @return The found entity, or null if the entity is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public abstract T getById(Long id) throws HibernateException;

    private Session getThreadLocalSession() throws org.hibernate.HibernateException {
        Session currentSession = threadLocalSession.get();
        if (currentSession == null || !currentSession.isOpen()) {
            currentSession = sessionFactory.openSession();
            threadLocalSession.set(currentSession);
            logger.debug("Opened new Hibernate session");
        }
        return currentSession;
    }

    /**
     * Execute the provided action inside a Hibernate transaction. This method handles the beginning and committing of
     * transactions and provides proper error handling, including transaction rollback in case of an exception.
     *
     * @param action The action to be executed inside the transaction. It takes a {@code Session} as a parameter.
     * @throws HibernateException If an error occurs during the Hibernate transaction.
     */
    protected final void actionInsideOfTransaction(Consumer<Session> action) throws org.hibernate.HibernateException {
        transaction = session.getTransaction();
        try {
            transaction.begin();
            action.accept(session);
            transaction.commit();
            logger.debug("Transaction committed successfully");
        } catch (org.hibernate.HibernateException exception) {
            logger.error("Error during Hibernate transaction", exception);
            if (transaction.isActive()) {
                transaction.rollback();
                logger.debug("Transaction rolled back");
            }
            throw new org.hibernate.HibernateException("Unable to add data to the database", exception);
        }
    }

    /**
     * Close the Hibernate session associated with this repository. This method ensures that the session is closed and
     * removes it from the {@code ThreadLocal} storage.
     *
     * @throws HibernateException If an error occurs while closing the Hibernate session.
     */
    @Override
    public final void close() throws org.hibernate.HibernateException {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                threadLocalSession.remove();
                logger.debug("Closed Hibernate session");
            }
        } catch (org.hibernate.HibernateException e) {
            logger.error("Error closing Hibernate session", e);
        }
    }

    /**
     * Delete the specified entity from the database. This method encapsulates the deletion operation inside a Hibernate
     * transaction.
     *
     * @param object The entity to be deleted.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public final boolean delete(T object) throws org.hibernate.HibernateException {
        actionInsideOfTransaction(session -> session.remove(object));
        logger.info("Entity deleted successfully");
        return true;
    }

    /**
     * Save the specified entity to the database. This method encapsulates the save operation inside a Hibernate transaction.
     *
     * @param object The entity to be saved.
     * @return True if the entity was saved successfully.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public final boolean save(T object) throws org.hibernate.HibernateException {
        actionInsideOfTransaction(session -> session.persist(object));
        logger.info("Entity saved successfully");
        return true;
    }

    /**
     * Update the specified entity in the database. This method encapsulates the update operation inside a Hibernate
     * transaction.
     *
     * @param object The entity to be updated.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public final boolean update(T object) throws org.hibernate.HibernateException {
        actionInsideOfTransaction(session -> session.merge(object));
        logger.info("Entity updated successfully");
        return true;
    }

    /**
     * Delete multiple entities from the database. This method encapsulates the deletion operation inside a Hibernate
     * transaction.
     *
     * @param entities The collection of books to be deleted.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public final void deleteAll(Collection<T> entities) throws HibernateException {
        actionInsideOfTransaction(session -> {
            for (T entity : entities) {
                session.remove(entity);
                logger.info("Entity with ID {} deleted successfully", entity.getId());
            }
        });
    }
}