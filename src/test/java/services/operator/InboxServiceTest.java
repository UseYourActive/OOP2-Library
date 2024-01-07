package services.operator;

import com.library.backend.services.operator.InboxService;
import com.library.database.repositories.EventNotificationRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class InboxServiceTest {
    private InboxService service;
    private EventNotificationRepository eventNotificationRepository;

    @BeforeEach
    void setUp() {
        eventNotificationRepository = mock(EventNotificationRepository.class);
        service = new InboxService(eventNotificationRepository);
    }
}
