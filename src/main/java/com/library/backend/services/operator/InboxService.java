package com.library.backend.services.operator;

import com.library.backend.services.Service;
import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import com.library.database.repositories.EventNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InboxService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(InboxService.class);
    private final EventNotificationRepository eventNotificationRepository;

    public InboxService(EventNotificationRepository eventNotificationRepository) {
        this.eventNotificationRepository = eventNotificationRepository;
    }

    public List<EventNotification> getEventNotifications(User user) {
        try {
            List<EventNotification> allEventNotifications = eventNotificationRepository.findAll();
            List<EventNotification> userEventNotifications = allEventNotifications.stream()
                    .filter(event -> event.getUser().equals(user))
                    .toList();

            logger.info("Retrieved {} event notifications for user: {}", userEventNotifications.size(), user.getUsername());
            return userEventNotifications;

        } catch (Exception e) {
            logger.error("Failed to retrieve event notifications for user: {}", user.getUsername(), e);
            throw new RuntimeException("Failed to retrieve event notifications.", e);
        }
    }
}
