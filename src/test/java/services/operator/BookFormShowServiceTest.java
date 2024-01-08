package services.operator;

import com.library.backend.exception.ReturnBookException;
import com.library.backend.exception.email.EmailException;
import com.library.backend.services.EmailSenderService;
import com.library.backend.services.operator.BookFormShowService;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.EventNotification;
import com.library.database.entities.Reader;
import com.library.database.enums.BookFormStatus;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.EventNotificationRepository;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;

public class BookFormShowServiceTest {
    @InjectMocks private BookFormShowService service;
    @Mock private BookRepository bookRepository;
    @Mock private ReaderRepository readerRepository;
    @Mock private EventNotificationRepository eventNotificationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadEmailSettings_Successful() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        // Act
        service.loadEmailSettings(username, password);

        // Assert
        assertNotNull(service.getEmailSenderService());
    }
}
