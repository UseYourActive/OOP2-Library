package services.operator;

import com.library.backend.services.operator.InboxService;
import com.library.database.entities.EventNotification;
import com.library.database.entities.User;
import com.library.database.repositories.EventNotificationRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InboxServiceTest {
    @InjectMocks private InboxService service;
    @Mock private EventNotificationRepository eventNotificationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEventNotifications() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        EventNotification event1 = new EventNotification();
        event1.setUser(user);

        EventNotification event2 = new EventNotification();
        event2.setUser(user);

        when(eventNotificationRepository.findAll()).thenReturn(List.of(event1, event2));

        // Act
        List<EventNotification> result = service.getEventNotifications(user);

        // Assert
        assertEquals(2, result.size());
        assertEquals(user, result.get(0).getUser());
        assertEquals(user, result.get(1).getUser());
    }

    @Test
    void testGetEventNotificationsEmptyList() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        when(eventNotificationRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<EventNotification> result = service.getEventNotifications(user);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEventNotificationsException() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        when(eventNotificationRepository.findAll()).thenThrow(new HibernateException("Test Exception"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> service.getEventNotifications(user));
    }

    @Test
    void testGetEventNotificationsForDifferentUser() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        User anotherUser = new User();
        anotherUser.setUsername("jane_doe");

        EventNotification event1 = new EventNotification();
        event1.setUser(user);

        EventNotification event2 = new EventNotification();
        event2.setUser(anotherUser);

        when(eventNotificationRepository.findAll()).thenReturn(List.of(event1, event2));

        // Act
        List<EventNotification> result = service.getEventNotifications(user);

        // Assert
        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getUser());
    }

    @Test
    void testGetEventNotificationsWithNullResultFromRepository() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        when(eventNotificationRepository.findAll()).thenReturn(null);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> service.getEventNotifications(user));
    }

    @Test
    void testGetEventNotificationsWithNullUserInEvent() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");

        EventNotification event1 = new EventNotification();
        event1.setUser(null);

        when(eventNotificationRepository.findAll()).thenReturn(List.of(event1));

        // Act
        List<EventNotification> result = service.getEventNotifications(user);

        // Assert
        assertTrue(result.isEmpty());
    }
}
