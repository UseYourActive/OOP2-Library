package com.library.backend.services.operator;

import com.library.backend.services.Service;
import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import com.library.database.repositories.EventNotificationRepository;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The {@code InboxService} class provides services for managing event notifications related to users.
 * It includes functionality to retrieve event notifications for a specific user.
 *
 * @see Service
 */
public class InboxService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(InboxService.class);
    private final EventNotificationRepository eventNotificationRepository;

    /**
     * Constructs an {@code InboxService} instance with the specified event notification repository.
     *
     * @param eventNotificationRepository The repository for managing event notifications.
     */
    public InboxService(EventNotificationRepository eventNotificationRepository) {
        this.eventNotificationRepository = eventNotificationRepository;
    }

    /**
     * Retrieves a list of event notifications associated with the specified user.
     *
     * @param user The user for whom event notifications are to be retrieved.
     * @return A list of event notifications for the specified user.
     * @throws RuntimeException If an error occurs while retrieving event notifications.
     */
    public List<EventNotification> getEventNotifications(User user) {
        try {
            List<EventNotification> allNotifications = eventNotificationRepository.findAll();

            if (allNotifications != null) {
                return allNotifications.stream()
                        .filter(notification -> {
                            User notificationUser = notification.getUser();
                            return notificationUser != null && notificationUser.equals(user);
                        })
                        .toList();
            } else {
                throw new RuntimeException("Failed to retrieve event notifications. Repository returned null.");
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Failed to retrieve event notifications.", e);
        }
    }

    //    public List<EventNotification> getEventNotifications(User user) {
//        try {
//            List<EventNotification> allEventNotifications = eventNotificationRepository.findAll();
//            List<EventNotification> userEventNotifications = allEventNotifications.stream()
//                    .filter(event -> event.getUser().equals(user))
//                    .toList();
//
//            logger.info("Retrieved {} event notifications for user: {}", userEventNotifications.size(), user.getUsername());
//            return userEventNotifications;
//
//        } catch (Exception e) {
//            logger.error("Failed to retrieve event notifications for user: {}", user.getUsername(), e);
//            throw new RuntimeException("Failed to retrieve event notifications.", e);
//        }
//    }
}
