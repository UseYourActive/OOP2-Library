package services.operator;

import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.services.operator.CreateBookFormService;
import com.library.database.entities.Reader;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class CreateBookFormServiceTest {
    private CreateBookFormService service;
    private ReaderRepository readerRepository;
    private BookFormRepository bookFormRepository;
    private BookRepository bookRepository;
    private SearchEngine<Reader> searchEngine;

    @BeforeEach
    void setUp() {
        searchEngine = new ReaderSearchEngine();
        readerRepository = mock(ReaderRepository.class);
        bookFormRepository = mock(BookFormRepository.class);
        bookRepository = mock(BookRepository.class);
        service = new CreateBookFormService(readerRepository, bookFormRepository, bookRepository);
        service.setReaderSearchEngine(searchEngine);
    }
}
