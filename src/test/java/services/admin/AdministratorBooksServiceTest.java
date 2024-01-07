package services.admin;

import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.services.admin.AdministratorBooksService;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class AdministratorBooksServiceTest {
    private BookInventoryRepository bookInventoryRepository;
    private BookFormRepository bookFormRepository;
    private SearchEngine<BookInventory> searchEngine;
    private AdministratorBooksService service;

    @BeforeEach
    void setUp() {
        bookInventoryRepository = mock(BookInventoryRepository.class);
        bookFormRepository = mock(BookFormRepository.class);
        searchEngine = new BookInventorySearchEngine();

        service = new AdministratorBooksService(bookInventoryRepository, bookFormRepository);
        service.setSearchEngine(searchEngine);
    }
}
