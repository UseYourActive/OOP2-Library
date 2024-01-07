package services.operator;

import com.library.backend.services.operator.CreateReaderProfileService;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class CreateReaderProfileServiceTest {
    private CreateReaderProfileService service;
    private ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        readerRepository = mock(ReaderRepository.class);
        service = new CreateReaderProfileService(readerRepository);
    }
}
