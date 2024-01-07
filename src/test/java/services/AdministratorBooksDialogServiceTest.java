package services;

import com.library.backend.services.admin.AdministratorBooksDialogService;
import com.library.database.entities.Book;
import com.library.database.entities.BookInventory;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AdministratorBooksDialogServiceTest {
    private BookInventoryRepository bookInventoryRepository;
    private BookFormRepository bookFormRepository;
    private AdministratorBooksDialogService service;

    @BeforeEach
    void setUp() {
        bookInventoryRepository = mock(BookInventoryRepository.class);
        bookFormRepository = mock(BookFormRepository.class);
        service = new AdministratorBooksDialogService(bookInventoryRepository, bookFormRepository);
    }

    @Test
    void removeSelectedBooks_RepresentativeBookRemoval_DeletesBookInventory() {
        // Arrange
        BookInventory bookInventory = new BookInventory();
        Book bookToRemove = new Book();
        bookInventory.setRepresentiveBook(bookToRemove);
        bookInventory.setBookList(List.of(bookToRemove));
        List<Book> booksToRemove = List.of(bookToRemove);

        // Act
        service.removeSelectedBooks(bookInventory, booksToRemove);

        // Assert
        verify(bookInventoryRepository, times(1)).delete(bookInventory);
        verifyNoMoreInteractions(bookInventoryRepository);
    }

    @Test
    void removeSelectedBooks_NoBooksLeftInInventory_SaveBookInventory() {
        // Arrange
        BookInventory bookInventory = new BookInventory();
        Book bookToRemove = new Book();
        bookInventory.setRepresentiveBook(bookToRemove);
        bookInventory.setBookList(Arrays.asList(bookToRemove));

        List<Book> booksToRemove = Arrays.asList(bookToRemove);

        // Act
        service.removeSelectedBooks(bookInventory, booksToRemove);

        // Assert
        verify(bookInventoryRepository, times(1)).delete(bookInventory);
        verifyNoMoreInteractions(bookInventoryRepository);
    }
}
