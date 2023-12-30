package repositories;

import com.library.database.entities.Reader;
import com.library.database.repositories.ReaderRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReaderRepositoryTest {

    @Mock
    private Session session;

    @InjectMocks
    private ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readerRepository.setSession(session);
    }

    @Test
    void findById_NonExistingReader_ShouldReturnEmptyOptional() {
        // Arrange
        Long nonExistingReaderId = 999L;

        // Act
        Optional<Reader> result = readerRepository.findById(nonExistingReaderId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getById_NonExistingReader_ShouldReturnNull() {
        // Arrange
        Long nonExistingReaderId = 999L;

        // Act
        Reader result = readerRepository.getById(nonExistingReaderId);

        // Assert
        assertNull(result);
    }

    @Test
    void findById_ExistingReader_ShouldReturnReader() {
        // Arrange
        Long readerId = 1L;
        Reader expectedReader = new Reader();
        expectedReader.setId(readerId);

        when(session.get(Reader.class, readerId)).thenReturn(expectedReader);

        // Act
        Optional<Reader> result = readerRepository.findById(readerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(readerId, result.get().getId());
    }
}

