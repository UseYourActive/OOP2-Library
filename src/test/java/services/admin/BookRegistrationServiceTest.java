package services.admin;

import com.library.backend.services.admin.BookRegistrationService;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class BookRegistrationServiceTest {
    private BookInventoryRepository bookInventoryRepository;
    private BookRepository bookRepository;
    private BookRegistrationService service;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookInventoryRepository = mock(BookInventoryRepository.class);
        service = new BookRegistrationService(bookInventoryRepository, bookRepository);
    }
}
