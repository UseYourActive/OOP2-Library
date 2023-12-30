package repositories;

import com.library.database.entities.BookForm;
import com.library.database.repositories.BookFormRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookFormRepositoryTest {

    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @InjectMocks
    private BookFormRepository bookFormRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock for the Session
        session = mock(Session.class);

        // Mock transaction creation to return a valid transaction
        transaction = mock(Transaction.class);
        when(session.beginTransaction()).thenReturn(transaction);

        bookFormRepository = BookFormRepository.getInstance();
        bookFormRepository.setSession(session);
        bookFormRepository.setTransaction(transaction);
    }

    @Test
    void findById_BookFormFound_ReturnsBookFormOptional() {
        // Arrange
        Long bookFormId = 1L;
        BookForm expectedBookForm = new BookForm();
        expectedBookForm.setId(bookFormId);

        when(session.get(BookForm.class, bookFormId)).thenReturn(expectedBookForm);

        // Act
        Optional<BookForm> result = bookFormRepository.findById(bookFormId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedBookForm, result.get());
        verify(session).get(BookForm.class, bookFormId);
    }

    @Test
    void findById_BookFormNotFound_ReturnsEmptyOptional() {
        // Arrange
        Long bookFormId = 1L;

        when(session.get(BookForm.class, bookFormId)).thenReturn(null);

        // Act
        Optional<BookForm> result = bookFormRepository.findById(bookFormId);

        // Assert
        assertTrue(result.isEmpty());
        verify(session).get(BookForm.class, bookFormId);
    }

    @Test
    void findAll_MultipleBookForms_ReturnsListOfBookForms() {
        // Arrange
        BookForm bookForm1 = new BookForm();
        BookForm bookForm2 = new BookForm();
        List<BookForm> expectedBookForms = Arrays.asList(bookForm1, bookForm2);

        Query<BookForm> query = mock(Query.class);
        when(session.createQuery(eq("SELECT b FROM BookForm b"), eq(BookForm.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedBookForms);

        // Act
        List<BookForm> result = bookFormRepository.findAll();

        // Assert
        assertEquals(expectedBookForms.size(), result.size());
        assertTrue(result.containsAll(expectedBookForms));
        verify(session).createQuery(eq("SELECT b FROM BookForm b"), eq(BookForm.class));
    }

    @Test
    void findAll_NoBookForms_ReturnsEmptyList() {
        // Arrange
        List<BookForm> expectedBookForms = Arrays.asList();

        Query<BookForm> query = mock(Query.class);
        when(session.createQuery(eq("SELECT b FROM BookForm b"), eq(BookForm.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedBookForms);

        // Act
        List<BookForm> result = bookFormRepository.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(session).createQuery(eq("SELECT b FROM BookForm b"), eq(BookForm.class));
    }

    @Test
    void getById_BookFormFound_ReturnsBookForm() {
        // Arrange
        Long bookFormId = 1L;
        BookForm expectedBookForm = new BookForm();
        expectedBookForm.setId(bookFormId);

        when(session.get(BookForm.class, bookFormId)).thenReturn(expectedBookForm);

        // Act
        BookForm result = bookFormRepository.getById(bookFormId);

        // Assert
        assertEquals(expectedBookForm, result);
        verify(session).get(BookForm.class, bookFormId);
    }

    @Test
    void getById_BookFormNotFound_ReturnsNull() {
        // Arrange
        Long bookFormId = 1L;

        when(session.get(BookForm.class, bookFormId)).thenReturn(null);

        // Act
        BookForm result = bookFormRepository.getById(bookFormId);

        // Assert
        assertNull(result);
        verify(session).get(BookForm.class, bookFormId);
    }

    @Test
    void findGenreByName_BookFormNotFound_ReturnsEmptyOptional() {
        // Arrange
        String name = "nonExistentBookForm";

        Query<BookForm> query = mock(Query.class);
        when(session.createQuery(anyString(), eq(BookForm.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        // Act
        Optional<BookForm> result = bookFormRepository.findGenreByName(name);

        // Assert
        assertTrue(result.isEmpty());
        verify(session).createQuery(anyString(), eq(BookForm.class));
    }

    @Test
    void findGenreByName_BookFormFound_ReturnsBookFormOptional() {
        // Arrange
        String name = "testBookForm";
        BookForm expectedBookForm = new BookForm();

        Query<BookForm> query = mock(Query.class);
        when(session.createQuery(anyString(), eq(BookForm.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.of(expectedBookForm));

        // Act
        Optional<BookForm> result = bookFormRepository.findGenreByName(name);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedBookForm, result.get());
        verify(session).createQuery(anyString(), eq(BookForm.class));
    }

}
