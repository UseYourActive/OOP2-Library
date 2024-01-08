package services.operator;

import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.services.operator.OperatorReadersService;
import com.library.database.entities.Reader;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class OperatorReadersServiceTest {
    private OperatorReadersService service;
    private SearchEngine<Reader> searchEngine;
    private ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        searchEngine = new ReaderSearchEngine();
        readerRepository = mock(ReaderRepository.class);
        service = new OperatorReadersService(readerRepository);
        service.setReaderSearchEngine(searchEngine);
    }
}
