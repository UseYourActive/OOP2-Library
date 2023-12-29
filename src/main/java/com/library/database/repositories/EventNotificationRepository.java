package com.library.database.repositories;

import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * The {@code EventNotificationRepository} class provides methods to interact with the database for managing
 * {@code EventNotification} entities. It extends the {@code Repository} class and implements operations such as finding
 * notifications by ID, retrieving all notifications, getting notifications by ID, finding notifications by user, and
 * saving notifications.
 *
 * <p>The class follows the singleton pattern to ensure a single instance throughout the application.</p>
 *
 * @see Repository
 * @see EventNotification
 */
public class EventNotificationRepository extends Repository<EventNotification> {
    private static final Logger logger = LoggerFactory.getLogger(EventNotificationRepository.class);
    private static volatile EventNotificationRepository instance;

    private EventNotificationRepository() {
    }

    /**
     * Get the singleton instance of the {@code EventNotificationRepository}. If the instance does not exist, it is created
     * in a thread-safe manner using double-checked locking.
     *
     * @return The singleton instance of the {@code EventNotificationRepository}.
     */
    public static EventNotificationRepository getInstance() {
        if (instance == null) {
            synchronized (EventNotificationRepository.class) {
                if (instance == null) {
                    instance = new EventNotificationRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Find and return an event notification by its unique identifier (ID). If the notification is not found, an empty
     * {@code Optional} is returned.
     *
     * @param id The ID of the event notification to find.
     * @return An {@code Optional} containing the found event notification, or empty if the notification is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public Optional<EventNotification> findById(Long id) throws HibernateException {
        try {
            EventNotification notification = session.get(EventNotification.class, id);
            if (notification != null) {
                logger.info("Successfully found event notification with id: {}", id);
            } else {
                logger.info("No event notification found with id: {}", id);
                // maybe throw an exception?
            }
            return Optional.ofNullable(notification);
        } catch (HibernateException e) {
            logger.error("Error finding event notification by ID: {}", id, e);
            throw e;
        }
    }

    /**
     * Retrieve and return a list of all event notifications. This method uses HQL (Hibernate Query Language) to execute a
     * query to fetch all notifications.
     *
     * @return A list containing all event notifications.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public List<EventNotification> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT n FROM EventNotification n", EventNotification.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all event notifications", e);
            throw e;
        }
    }

    /**
     * Get and return an event notification by its unique identifier (ID). If the notification is not found, it returns
     * null.
     *
     * @param id The ID of the event notification to get.
     * @return The found event notification, or null if the notification is not found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    @Override
    public EventNotification getById(Long id) {
        EventNotification notification = session.get(EventNotification.class, id);
        if (notification != null) {
            logger.info("Successfully found event notification with id: {}", id);
        }
        return notification;
    }

    /**
     * Executes a Hibernate query with parameters and returns the result as an optional value. The method uses the provided
     * query, parameter name, and parameter value to create a Hibernate query, sets the parameters, and retrieves a single
     * result.
     *
     * <p>If the query execution returns no result, an empty {@code Optional} is returned. If an exception occurs during the
     * execution, the method logs an error and throws a {@code HibernateException}.</p>
     *
     * @param <T>        The type of the result.
     * @param query      The HQL (Hibernate Query Language) query string.
     * @param paramName  The name of the parameter in the query.
     * @param paramValue The value of the parameter in the query.
     * @param resultType The class type of the expected result.
     * @return An {@code Optional} containing the result of the query, or empty if no result is found.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     * @throws NoResultException  If no result is found for the query.
     */
    private <T> Optional<T> executeQuery(String query, String paramName, String paramValue, Class<T> resultType) throws HibernateException, NoResultException {
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
     * Find and return a list of event notifications associated with the specified user.
     *
     * @param user The user for whom to find event notifications.
     * @return A list containing event notifications associated with the specified user.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public List<EventNotification> findByUser(User user) throws HibernateException {
        String query = "SELECT n FROM EventNotification n WHERE n.user = :user";
        return session.createQuery(query, EventNotification.class)
                .setParameter("user", user)
                .getResultList();
    }

    /**
     * Save the specified event notification to the database. This method encapsulates the save operation inside a Hibernate
     * transaction.
     *
     * @param notification The event notification to be saved.
     * @throws HibernateException If an error occurs during the Hibernate operation.
     */
    public void saveNotification(EventNotification notification) throws HibernateException {
        actionInsideOfTransaction(session -> session.persist(notification));
        logger.info("Event notification saved successfully");
    }
}