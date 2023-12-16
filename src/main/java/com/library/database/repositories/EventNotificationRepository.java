package com.library.database.repositories;

import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class EventNotificationRepository extends Repository<EventNotification> {
    private static final Logger logger = LoggerFactory.getLogger(EventNotificationRepository.class);
    private static volatile EventNotificationRepository instance;

    private EventNotificationRepository() {
    }

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

    @Override
    public List<EventNotification> findAll() throws HibernateException {
        try {
            return session.createQuery("SELECT n FROM EventNotification n", EventNotification.class).getResultList();
        } catch (HibernateException e) {
            logger.error("Error retrieving all event notifications", e);
            throw e;
        }
    }

    @Override
    public EventNotification getById(Long id) {
        EventNotification notification = session.get(EventNotification.class, id);
        if (notification != null) {
            logger.info("Successfully found event notification with id: {}", id);
        }
        return notification;
    }

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

    public List<EventNotification> findByUser(User user) throws HibernateException {
        String query = "SELECT n FROM EventNotification n WHERE n.user = :user";
        return session.createQuery(query, EventNotification.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void saveNotification(EventNotification notification) throws HibernateException {
        executeInsideTransaction(session -> session.persist(notification));
        logger.info("Event notification saved successfully");
    }
}