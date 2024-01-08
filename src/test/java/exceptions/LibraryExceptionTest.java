package exceptions;

import com.library.backend.exception.*;
import com.library.backend.exception.email.EmailException;
import com.library.backend.exception.email.TransportException;
import com.library.backend.exception.searchengine.BookInventorySearchEngineException;
import com.library.backend.exception.searchengine.OperatorSearchEngineException;
import com.library.backend.exception.searchengine.ReaderSearchEngineException;
import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.exception.users.AdminNotFoundException;
import com.library.backend.exception.users.OperatorNotFoundException;
import com.library.backend.exception.users.ReaderNotFoundException;
import com.library.backend.exception.users.UserNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryExceptionTest {
    @Test
    void testIncorrectInputException() {
        assertThrows(IncorrectInputException.class, () -> {
            throw new IncorrectInputException("Incorrect input");
        });
    }

    @Test
    void testInvalidQuantityException() {
        assertThrows(InvalidQuantityException.class, () -> {
            throw new InvalidQuantityException("Invalid quantity");
        });
    }

    @Test
    void testLibraryException() {
        assertThrows(LibraryException.class, () -> {
            throw new LibraryException("General library exception");
        });
    }

    @Test
    void testNonExistentServiceException() {
        assertThrows(NonExistentServiceException.class, () -> {
            throw new NonExistentServiceException("Non-existent service");
        });
    }

    @Test
    void testObjectCannotBeNullException() {
        assertThrows(ObjectCannotBeNullException.class, () -> {
            throw new ObjectCannotBeNullException("Object cannot be null");
        });
    }

    @Test
    void testReaderException() {
        assertThrows(ReaderException.class, () -> {
            throw new ReaderException("Reader exception");
        });
    }

    @Test
    void testReturnBookException() {
        assertThrows(ReturnBookException.class, () -> {
            throw new ReturnBookException("Return book exception");
        });
    }

    @Test
    void testUserExistException() {
        assertThrows(UserExistException.class, () -> {
            throw new UserExistException("User already exists");
        });
    }

    @Test
    void testUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException("User not found");
        });
    }

    @Test
    void testReaderNotFoundException() {
        assertThrows(ReaderNotFoundException.class, () -> {
            throw new ReaderNotFoundException("Reader not found");
        });
    }

    @Test
    void testOperatorNotFoundException() {
        assertThrows(OperatorNotFoundException.class, () -> {
            throw new OperatorNotFoundException("Operator not found");
        });
    }

    @Test
    void testAdminNotFoundException() {
        assertThrows(AdminNotFoundException.class, () -> {
            throw new AdminNotFoundException("Admin not found");
        });
    }

    @Test
    void testSearchEngineException() {
        assertThrows(SearchEngineException.class, () -> {
            throw new SearchEngineException("Search engine error");
        });
    }

    @Test
    void testReaderSearchEngineException() {
        assertThrows(ReaderSearchEngineException.class, () -> {
            throw new ReaderSearchEngineException("Reader search engine error");
        });
    }

    @Test
    void testOperatorSearchEngineException() {
        assertThrows(OperatorSearchEngineException.class, () -> {
            throw new OperatorSearchEngineException("Operator search engine error");
        });
    }

    @Test
    void testBookInventorySearchEngineException() {
        assertThrows(BookInventorySearchEngineException.class, () -> {
            throw new BookInventorySearchEngineException("Book inventory search engine error");
        });
    }

    @Test
    void testEmailException() {
        assertThrows(EmailException.class, () -> {
            throw new EmailException("Email error");
        });
    }

    @Test
    void testTransportException() {
        assertThrows(TransportException.class, () -> {
            throw new TransportException("Transport error");
        });
    }
}
