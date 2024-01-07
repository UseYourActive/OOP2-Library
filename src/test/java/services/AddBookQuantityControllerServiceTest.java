package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.library.backend.exception.InvalidQuantityException;
import com.library.backend.exception.ObjectCannotBeNullException;
import com.library.backend.services.admin.AddBookQuantityControllerService;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AddBookQuantityControllerServiceTest {
    private AddBookQuantityControllerService service;
    private BookRepository bookRepository;
    private BookInventoryRepository bookInventoryRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookInventoryRepository = mock(BookInventoryRepository.class);
        service = new AddBookQuantityControllerService(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_ValidQuantity_ShouldAddBooksToInventory() throws InvalidQuantityException {
        // Arrange
        String quantityString = "3";
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        bookInventory.setRepresentiveBook(new Book());

        // Act
        assertDoesNotThrow(() -> service.increaseBookQuantity(quantityString, bookInventory));

        // Assert
        verify(bookRepository, times(3)).save(any());
        verify(bookInventoryRepository, times(1)).update(any());
        verifyNoMoreInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_InvalidQuantity_ShouldThrowException() {
        // Arrange
        String invalidQuantity = "-2";
        BookInventory bookInventory = new BookInventory();

        // Act & Assert
        assertThrows(InvalidQuantityException.class, () -> service.increaseBookQuantity(invalidQuantity, bookInventory));

        // No interactions with repositories should occur in case of an exception
        verifyNoMoreInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_InvalidQuantity_ShouldThrowInvalidQuantityException() {
        // Arrange
        String quantityString = "-2"; // or any invalid quantity
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        bookInventory.setRepresentiveBook(new Book());

        // Act & Assert
        assertThrows(InvalidQuantityException.class,
                () -> service.increaseBookQuantity(quantityString, bookInventory)
        );
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_NonNumericQuantity_ShouldThrowNumberFormatException() {
        // Arrange
        String quantityString = "abc"; // or any non-numeric input
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        bookInventory.setRepresentiveBook(new Book());

        // Act & Assert
        assertThrows(NumberFormatException.class,
                () -> service.increaseBookQuantity(quantityString, bookInventory)
        );
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_ZeroQuantity_ShouldNotSaveOrUpdate() throws InvalidQuantityException {
        // Arrange
        String quantityString = "0";
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        bookInventory.setRepresentiveBook(new Book());

        // Act
        assertThrows(InvalidQuantityException.class, () -> service.increaseBookQuantity(quantityString, bookInventory));

        // Assert
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_NegativeQuantity_ShouldThrowInvalidQuantityException() {
        // Arrange
        String quantityString = "-3";
        BookInventory bookInventory = new BookInventory();

        // Act & Assert
        assertThrows(InvalidQuantityException.class, () -> service.increaseBookQuantity(quantityString, bookInventory));
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_ValidQuantityWithExistingBooks_ShouldAddBooksToInventory() throws InvalidQuantityException {
        // Arrange
        String quantityString = "2";
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        Book existingBook = new Book();
        bookInventory.addBook(existingBook);
        bookInventory.setRepresentiveBook(existingBook);

        // Act
        assertDoesNotThrow(() -> service.increaseBookQuantity(quantityString, bookInventory));

        // Assert
        verify(bookRepository, times(2)).save(any());
        verify(bookInventoryRepository, times(1)).update(any());
        verifyNoMoreInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_InvalidBookQuantity_ShouldThrowNumberFormatException() {
        // Arrange
        String quantityString = "abc";
        BookInventory bookInventory = new BookInventory();

        // Act & Assert
        assertThrows(NumberFormatException.class, () -> service.increaseBookQuantity(quantityString, bookInventory));
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_NullBookInventory_ShouldThrowIllegalArgumentException() {
        // Arrange
        String quantityString = "2";

        // Act & Assert
        assertThrows(ObjectCannotBeNullException.class, () -> service.increaseBookQuantity(quantityString, null));
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }


    @Test
    void increaseBookQuantity_ValidQuantityWithRepresentativeBookNull_ShouldThrowNullPointerException() {
        // Arrange
        String quantityString = "3";
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> service.increaseBookQuantity(quantityString, bookInventory));
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_InvalidQuantityFormat_ShouldThrowNumberFormatException() {
        // Arrange
        String invalidQuantityString = "abc";
        BookInventory bookInventory = new BookInventory();

        // Act & Assert
        assertThrows(NumberFormatException.class, () -> service.increaseBookQuantity(invalidQuantityString, bookInventory));
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_InvalidQuantity_ZeroBooksAdded_ShouldThrowInvalidQuantityException() {
        // Arrange
        String quantityString = "0";
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        bookInventory.setRepresentiveBook(new Book());

        // Act & Assert
        assertThrows(InvalidQuantityException.class, () -> service.increaseBookQuantity(quantityString, bookInventory));

        // Verify
        verifyNoInteractions(bookRepository, bookInventoryRepository);
    }

    @Test
    void increaseBookQuantity_ValidQuantity_ShouldAddBooksToInventoryAndSave() throws InvalidQuantityException {
        // Arrange
        String quantityString = "3";
        BookInventory bookInventory = new BookInventory();
        bookInventory.setBookList(new ArrayList<>());
        bookInventory.setRepresentiveBook(new Book());

        // Act
        assertDoesNotThrow(() -> service.increaseBookQuantity(quantityString, bookInventory));

        // Assert
        verify(bookRepository, times(3)).save(any());
        verify(bookInventoryRepository, times(1)).update(any());
        verifyNoMoreInteractions(bookRepository, bookInventoryRepository);
    }
}
