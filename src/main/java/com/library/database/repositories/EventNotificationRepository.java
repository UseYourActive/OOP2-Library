package com.library.database.repositories;

import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class EventNotificationRepository extends Repository<EventNotification> {
    private static final Logger logger = LoggerFactory.getLogger(EventNotificationRepository.class);

    @Override
    public Optional<EventNotification> findById(Long id) {
        try {
            EventNotification notification = session.get(EventNotification.class, id);
            return Optional.ofNullable(notification);
        } catch (org.hibernate.HibernateException e) {
            logger.error("Error finding event notification by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<EventNotification> findAll() {
        try {
            return session.createQuery("SELECT n FROM EventNotification n", EventNotification.class).getResultList();
        } catch (org.hibernate.HibernateException e) {
            logger.error("Error retrieving all event notifications", e);
            throw e;
        }
    }

    public List<EventNotification> findByUser(User user) {
        try {
            return session.createQuery("SELECT n FROM EventNotification n WHERE n.user = :user", EventNotification.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (org.hibernate.HibernateException e) {
            logger.error("Error finding event notifications for user: {}", user.getUsername(), e);
            throw e;
        }
    }

    public void saveNotification(EventNotification notification) {
        executeInsideTransaction(session -> session.persist(notification));
        logger.info("Event notification saved successfully");
    }
}
