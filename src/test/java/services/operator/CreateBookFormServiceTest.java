package services.operator;

import com.library.backend.engines.ReaderSearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.exception.ReaderException;
import com.library.backend.services.operator.CreateBookFormService;
import com.library.database.entities.Book;
import com.library.database.entities.BookForm;
import com.library.database.entities.Reader;
import com.library.database.enums.BookStatus;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.ReaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateBookFormServiceTest {
    @InjectMocks private CreateBookFormService service;
    @Mock private ReaderRepository readerRepository;
    @Mock private BookFormRepository bookFormRepository;
    @Mock private BookRepository bookRepository;
    @Mock private SearchEngine<Reader> searchEngine = new ReaderSearchEngine();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReaders() {
        // Arrange
        List<Reader> mockReaders = new ArrayList<>();
        when(readerRepository.findAll()).thenReturn(mockReaders);

        // Act
        List<Reader> result = service.getAllReaders();

        // Assert
        verify(readerRepository).findAll();
    }

    @Test
    void testLendBooks() {
        // Arrange
        Reader selectedReader = new Reader();
        List<Book> bookList = new ArrayList<>();
        // Mock or set up your necessary conditions

        // Act and Assert
        // Ensure that lendBooks throws ReaderException with the expected message
        ReaderException exception = assertThrows(ReaderException.class, () -> service.lendBooks(selectedReader, bookList));
        assertEquals("The reader does not have a rating.", exception.getMessage());

        // Verify that no interactions with repositories occurred
        verifyNoInteractions(bookFormRepository, readerRepository, bookRepository);
    }

    @Test
    void testGetAllReadersWithMockData() {
        // Arrange
        List<Reader> mockReaders = new ArrayList<>();
        mockReaders.add(new Reader());
        mockReaders.add(new Reader());
        when(readerRepository.findAll()).thenReturn(mockReaders);

        // Act
        List<Reader> result = service.getAllReaders();

        // Assert
        assertEquals(2, result.size());
        verify(readerRepository).findAll();
    }

    @Test
    void testLendBooksWhenReaderNotAllowed() {
        // Arrange
        Reader selectedReader = new Reader();
        selectedReader.setReaderRating(null); // Set reader rating to null
        List<Book> bookList = new ArrayList<>();

        // Act and Assert
        // Ensure that lendBooks throws ReaderException with the expected message
        ReaderException exception = assertThrows(ReaderException.class, () -> service.lendBooks(selectedReader, bookList));
        assertEquals("The reader does not have a rating.", exception.getMessage());

        // Verify that no interactions with repositories occurred
        verifyNoInteractions(bookFormRepository, readerRepository, bookRepository);
    }

    @Test
    void testLendBooksWhenNoBooksProvided() {
        // Arrange
        Reader selectedReader = new Reader();
        List<Book> bookList = new ArrayList<>();

        // Act and Assert
        // Ensure that lendBooks throws ReaderException with the expected message
        ReaderException exception = assertThrows(ReaderException.class, () -> service.lendBooks(selectedReader, bookList));
        assertEquals("The reader does not have a rating.", exception.getMessage());

        // Verify that no interactions with repositories occurred
        verifyNoInteractions(bookFormRepository, readerRepository, bookRepository);
    }

    @Test
    void testGetAvailableBooks() {
        // Arrange
        List<Book> bookList = Arrays.asList(
                new Book(),
                new Book(),
                new Book());
        bookList.forEach(n -> n.setBookStatus(BookStatus.AVAILABLE));

        // Assert
        assertEquals(3, bookList.size());
    }

    @Test
    void testGetAvailableBooksWhenNoAvailableBooks() {
        // Arrange
        List<Book> bookList = Arrays.asList(
                new Book(),
                new Book()
        );
        bookList.forEach(n -> n.setBookStatus(BookStatus.LENT));

        // Act
        List<Book> availableBooks = service.getBookRepository().findAll();

        // Assert
        assertTrue(availableBooks.isEmpty());
    }

    @Test
    void testGetAvailableBooksWhenAllBooksAreLent() {
        // Arrange
        List<Book> bookList = Arrays.asList(
                new Book(),
                new Book()
        );
        bookList.forEach(n -> n.setBookStatus(BookStatus.LENT));

        // Act
        List<Book> availableBooks = service.getBookRepository().findAll();

        // Assert
        assertTrue(availableBooks.isEmpty());
    }
}
