package services.operator;

import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.services.operator.OperatorBooksService;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.EventNotificationRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class OperatorBooksServiceTest {
    private OperatorBooksService service;
    private BookFormRepository bookFormRepository;
    private BookInventoryRepository bookInventoryRepository;
    private EventNotificationRepository eventNotificationRepository;
    private BookRepository bookRepository;
    private SearchEngine<BookInventory> searchEngine;

    @BeforeEach
    void setUp() {
        searchEngine = new BookInventorySearchEngine();
        bookFormRepository = mock(BookFormRepository.class);
        bookInventoryRepository = mock(BookInventoryRepository.class);
        eventNotificationRepository = mock(EventNotificationRepository.class);
        bookRepository = mock(BookRepository.class);
        service = new OperatorBooksService(bookFormRepository, bookInventoryRepository, eventNotificationRepository, bookRepository);
        service.setBookInventorySearchEngine(searchEngine);
    }
}
