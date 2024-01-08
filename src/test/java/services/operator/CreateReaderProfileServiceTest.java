package services.operator;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.operator.CreateReaderProfileService;
import com.library.database.entities.Reader;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateReaderProfileServiceTest {
    @InjectMocks private CreateReaderProfileService service;
    @Mock private ReaderRepository readerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReaderProfile() {
        // Arrange
        String firstName = "John";
        String middleName = "Doe";
        String lastName = "Smith";
        String phoneNumber = "1234567890";
        String email = "john.doe@example.com";

        // Mock necessary conditions
        when(readerRepository.save(any(Reader.class))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> service.createReader(firstName, middleName, lastName, phoneNumber, email));

        // Assert
        verify(readerRepository).save(any(Reader.class));
    }

    @Test
    void testCreateReaderProfileWithEmptyFirstName() {
        // Arrange
        String firstName = "";
        String middleName = "Doe";
        String lastName = "Smith";
        String phoneNumber = "1234567890";
        String email = "john.doe@example.com";

        // Act and Assert
        IncorrectInputException exception = assertThrows(IncorrectInputException.class,
                () -> service.createReader(firstName, middleName, lastName, phoneNumber, email));

        assertEquals("Please enter first name.", exception.getMessage());

        // Verify that no interaction with the repository occurred
        verifyNoInteractions(readerRepository);
    }

    @Test
    void testCreateReaderProfileWithIncorrectInput() {
        // Arrange
        String firstName = "John";
        String middleName = "Doe";
        String lastName = "";
        String phoneNumber = "";
        String email = "john.doe@example.com";

        // Act and Assert
        IncorrectInputException exception = assertThrows(IncorrectInputException.class,
                () -> service.createReader(firstName, middleName, lastName, phoneNumber, email));

        assertEquals("Please enter last name.", exception.getMessage());

        // Verify that no interaction with the repository occurred
        verifyNoInteractions(readerRepository);
    }
}
