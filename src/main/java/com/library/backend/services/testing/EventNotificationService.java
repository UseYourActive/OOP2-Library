package com.library.backend.services.testing;

import com.google.common.base.Preconditions;
import com.library.backend.services.Service;
import com.library.database.repositories.EventNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventNotificationService implements Service {
    private static final Logger logger = LoggerFactory.getLogger(EventNotificationService.class);
    private final EventNotificationRepository eventNotificationRepository;

    public EventNotificationService(EventNotificationRepository eventNotificationRepository) {
        this.eventNotificationRepository = Preconditions.checkNotNull(eventNotificationRepository, "EventNotificationRepository cannot be null");
    }
}
