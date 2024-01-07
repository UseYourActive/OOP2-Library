package services.operator;

import com.library.backend.services.operator.BookFormShowService;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.EventNotificationRepository;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class BookFormShowServiceTest {
    private BookFormShowService service;
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;
    private EventNotificationRepository eventNotificationRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        readerRepository = mock(ReaderRepository.class);
        eventNotificationRepository = mock(EventNotificationRepository.class);
        service = new BookFormShowService(bookRepository, readerRepository, eventNotificationRepository);
    }
}
