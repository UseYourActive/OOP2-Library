package services.operator;

import com.library.backend.exception.LibraryException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.database.entities.*;
import com.library.database.enums.BookFormStatus;
import com.library.database.enums.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.library.backend.engines.BookInventorySearchEngine;
import com.library.backend.engines.SearchEngine;
import com.library.backend.services.operator.OperatorBooksService;
import com.library.database.repositories.BookFormRepository;
import com.library.database.repositories.BookInventoryRepository;
import com.library.database.repositories.BookRepository;
import com.library.database.repositories.EventNotificationRepository;

public class OperatorBooksServiceTest {
    @InjectMocks private OperatorBooksService service;
    @Mock private BookFormRepository bookFormRepository;
    @Mock private BookInventoryRepository bookInventoryRepository;
    @Mock private EventNotificationRepository eventNotificationRepository;
    @Mock private BookRepository bookRepository;
    @Mock private SearchEngine<BookInventory> searchEngine = new BookInventorySearchEngine();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service.initializeSelectedBooks();
    }

    @Test
    void testInitializeSelectedBooks() {
        service.initializeSelectedBooks();
        assertEquals(0, service.getSelectedBooks().size());
    }

    @Test
    void testGetAllBookInventories() {
        // Mock data
        BookInventory bookInventory = new BookInventory();
        when(bookInventoryRepository.findAll()).thenReturn(Collections.singletonList(bookInventory));

        // Test the method
        List<BookInventory> result = service.getAllBookInventories();

        // Verify the interactions and assertions
        verify(bookInventoryRepository, times(1)).findAll();
        assertEquals(1, result.size());
    }


    @Test
    void testArchiveBook() throws LibraryException {
        // Mock data
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(true);

        // Test the method
        service.archiveBook(book);

        // Verify the interactions
        verify(bookRepository, times(1)).save(book);
        assertEquals(BookStatus.ARCHIVED, book.getBookStatus());
    }

    @Test
    void testAddSelectedBookToList() {
        // Mock data
        Book book = new Book();

        // Test the method
        service.addSelectedBookToList(book);

        // Verify the interactions and assertions
        assertNotNull(service.getSelectedBooks());
        assertEquals(1, service.getSelectedBooks().size());
        assertTrue(service.getSelectedBooks().contains(book));
    }

    @Test
    void testRemoveFromSelectedBooks() {
        // Mock data
        Book book = new Book();
        service.addSelectedBookToList(book);

        // Test the method
        service.removeFromSelectedBooks(book);

        // Verify the interactions and assertions
        assertNotNull(service.getSelectedBooks());
        assertEquals(0, service.getSelectedBooks().size());
    }
}
