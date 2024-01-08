package services.operator;

import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.operator.OperatorReadersService;
import com.library.database.entities.Reader;
import com.library.database.entities.ReaderRating;
import com.library.database.enums.Ratings;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OperatorReadersServiceTest {
    @InjectMocks private OperatorReadersService service;
    @Mock private SearchEngine<Reader> searchEngine = new ReaderSearchEngine();
    @Mock private ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReaders() {
        // Arrange
        List<Reader> readers = Arrays.asList(
                new Reader(1L, "John", "Doe", "Gotin1", "123456789", "john.doe@example.com",List.of(), new ReaderRating(1L, Ratings.FIVE_STAR, 3, 5)),
                new Reader(2L, "Jane", "Doe", "Gotin2", "987654321", "jane.doe@example.com", List.of(), new ReaderRating(1L, Ratings.FIVE_STAR, 3, 5))
        );
        when(readerRepository.findAll()).thenReturn(readers);

        // Act
        List<Reader> result = service.getAllReaders();

        // Assert
        assertEquals(readers, result);
        verify(readerRepository, times(1)).findAll();
    }

    @Test
    void testRemoveReader() {
        // Arrange
        Reader readerToRemove = new Reader(1L, "John", "Doe", "Gotin1", "123456789", "john.doe@example.com",List.of(), new ReaderRating(1L, Ratings.FIVE_STAR, 3, 5));

        // Act
        service.removeReader(readerToRemove);

        // Assert
        verify(readerRepository, times(1)).delete(readerToRemove);
    }
}
