package services.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.services.admin.BookRegistrationService;
import com.library.database.entities.BookInventory;
import com.library.database.enums.Genre;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookRegistrationServiceTest {
    @Mock
    private BookInventoryRepository bookInventoryRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookRegistrationService service;
    @Captor
    private ArgumentCaptor<BookInventory> bookInventoryCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewBook() throws IncorrectInputException {
        // Arrange
        String title = "The Great Gatsby";
        String author = "F. Scott Fitzgerald";
        String year = "1925";
        String resume = "A classic novel";
        Genre genre = Genre.FICTION;
        String amount = "5";

        // Mock repository methods
        when(bookInventoryRepository.findAll()).thenReturn(List.of());

        // Act
        service.registerNewBook(title, author, year, resume, genre, amount);

        // Assert
        verify(bookInventoryRepository).save(bookInventoryCaptor.capture());
        BookInventory savedInventory = bookInventoryCaptor.getValue();
        assertNotNull(savedInventory);
        assertEquals(title, savedInventory.getRepresentiveBook().getTitle());
        assertEquals(author, savedInventory.getRepresentiveBook().getAuthor().getName());
        assertEquals(5, savedInventory.getBookList().size());
    }

    @Test
    void testRegisterNewBookWithDefaultAmount() throws IncorrectInputException {
        // Arrange
        String title = "To Kill a Mockingbird";
        String author = "Harper Lee";
        String year = "1960";
        String resume = "A classic novel";
        Genre genre = Genre.FICTION;
        String amount = "1";

        // Mock repository methods
        when(bookInventoryRepository.findAll()).thenReturn(List.of());

        // Act
        service.registerNewBook(title, author, year, resume, genre, amount);

        // Assert
        verify(bookInventoryRepository).save(bookInventoryCaptor.capture());
        BookInventory savedInventory = bookInventoryCaptor.getValue();
        assertNotNull(savedInventory);
        assertEquals(1, savedInventory.getBookList().size());
    }

    @Test
    void testRegisterNewBookWithInvalidYear() {
        // Arrange
        String title = "Invalid Book";
        String author = "Invalid Author";
        String year = "InvalidYear";
        String resume = "Invalid book with invalid year";
        Genre genre = Genre.NON_FICTION;
        String amount = "1";

        // Act and Assert
        assertThrows(NumberFormatException.class, () ->
                service.registerNewBook(title, author, year, resume, genre, amount)
        );
    }

    @Test
    void testRegisterNewBookWithNonNumericAmount() {
        // Arrange
        String title = "The Great Gatsby";
        String author = "F. Scott Fitzgerald";
        String year = "1925";
        String resume = "A novel about the American Dream";
        Genre genre = Genre.FICTION;
        String amount = "invalidAmount";

        // Act and Assert
        assertThrows(NumberFormatException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithNegativeAmount() {
        // Arrange
        String title = "1984";
        String author = "George Orwell";
        String year = "1949";
        String resume = "A dystopian novel";
        Genre genre = Genre.SCIENCE_FICTION;
        String amount = "-5";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithEmptyTitle() {
        // Arrange
        String title = "";
        String author = "Author";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithNullAuthor() {
        // Arrange
        String title = "Book Title";
        String author = null;
        String year = "2023";
        String resume = "Book summary";
        Genre genre = Genre.MYSTERY;
        String amount = "3";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithNullGenre() {
        // Arrange
        String title = "Book with Null Genre";
        String author = "Author";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = null;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithInvalidAmountFormat() {
        // Arrange
        String title = "Book Title";
        String author = "Author";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = Genre.HORROR;
        String amount = "invalidAmount";

        // Act and Assert
        assertThrows(NumberFormatException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithNullTitle() {
        // Arrange
        String title = null;
        String author = "Author";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithEmptyAuthor() {
        // Arrange
        String title = "Book Title";
        String author = "";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithEmptyYear() {
        // Arrange
        String title = "Book Title";
        String author = "Author";
        String year = "";
        String resume = "Book summary";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(NumberFormatException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithNegativeYear() {
        // Arrange
        String title = "Book Title";
        String author = "Author";
        String year = "-2023";
        String resume = "Book summary";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithEmptyResume() {
        // Arrange
        String title = "Book Title";
        String author = "Author";
        String year = "2023";
        String resume = "";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithNullYear() {
        // Arrange
        String title = "Book Title";
        String author = "Author";
        String year = null;
        String resume = "Book summary";
        Genre genre = Genre.FICTION;
        String amount = "2";

        // Act and Assert
        assertThrows(NumberFormatException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithInvalidGenre() {
        // Arrange
        String title = "Book Title";
        String author = "Author";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = null;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }

    @Test
    void testRegisterNewBookWithInvalidAuthorFormat() {
        // Arrange
        String title = "Invalid Author Book";
        String author = "123";
        String year = "2023";
        String resume = "Book summary";
        Genre genre = Genre.MYSTERY;
        String amount = "2";

        // Act and Assert
        assertThrows(IncorrectInputException.class, () -> service.registerNewBook(title, author, year, resume, genre, amount));
    }
}
