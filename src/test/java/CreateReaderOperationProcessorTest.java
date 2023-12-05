import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.CreateReaderOperationProcessor;
import com.library.backend.operations.requests.CreateReaderRequest;
import com.library.backend.operations.responses.CreateReaderResponse;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateReaderOperationProcessorTest {
    private CreateReaderOperationProcessor readerProcessor;

    @BeforeEach
    void setUp() {
        ReaderRepository readerRepository = new ReaderRepository();
        CreateUserResponseConverter converter = new CreateUserResponseConverter();
        readerProcessor = new CreateReaderOperationProcessor(readerRepository, converter);
    }

//    @Test
//    void testCreateReaderSuccessfully() {
//        // Given
//        CreateReaderRequest request = new CreateReaderRequest("john.doe", "john@example.com",
//                "password123", "Doe", "Middle", "John");
//
//        // When
//        CreateReaderResponse response = readerProcessor.process(request);
//
//        // Then
//        assertNotNull(response);
//        assertNotNull(response.getUserId());
//        assertEquals(request.getUsername(), response.getUsername());
//        assertEquals(request.getFirstName(), response.getFirstName());
//        assertEquals(request.getLastName(), response.getLastName());
//    }
//
//    @Test
//    void testCreateReaderFailsAndThrowsException() {
//        // Given
//        CreateReaderRequest request = new CreateReaderRequest("john.doe", "john@example.com",
//                "password123", "Doe", "Middle", "John");
//
//        // Mock the reader repository to simulate a failure in saving.
//        // You may use a mocking framework like Mockito to do this.
//
//        // When
//        assertThrows(RuntimeException.class, () -> readerProcessor.process(request));
//
//        // Then
//    }
}
