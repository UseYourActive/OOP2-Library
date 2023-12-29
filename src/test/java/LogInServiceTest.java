import com.library.backend.exception.UserNotFoundException;
import com.library.backend.services.LogInService;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LogInServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LogInService logInService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("testUser");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        // Act
        User result = logInService.getUser(testUser);

        // Assert
        assertEquals(testUser, result);
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    public void testGetUserUserNotFound() {
        // Arrange
        User testUser = new User();
        testUser.setUsername("nonExistentUser");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(testUser));
        verify(userRepository).findByUsername("nonExistentUser");
    }

    @Test
    public void testGetUserWithNullUsername() {
        // Arrange
        User testUser = new User();
        testUser.setUsername(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(testUser));
        verify(userRepository).findByUsername(isNull());
    }


    @Test
    public void testGetUserWithEmptyUsername() {
        // Arrange
        User testUser = new User();
        testUser.setUsername("");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(testUser));
        verify(userRepository).findByUsername("");
    }

    @Test
    public void testGetUserWithWhitespaceUsername() {
        // Arrange
        User testUser = new User();
        testUser.setUsername("   ");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("   ")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(testUser));
        verify(userRepository).findByUsername("   ");
    }

    @Test
    public void testGetUserWithExistingUsername() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("existingUser");

        // Mocking behavior
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        // Act
        User resultUser = logInService.getUser(testUser);

        // Assert
        assertEquals(testUser, resultUser);
        verify(userRepository).findByUsername("existingUser");
    }

    @Test
    public void testGetUserWithNonexistentUsername() {
        // Arrange
        User testUser = new User();
        testUser.setUsername("nonexistentUser");

        // Mocking behavior
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(testUser));
        verify(userRepository).findByUsername("nonexistentUser");
    }

    @Test
    public void testGetUserWithValidCredentials() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("validUser");
        testUser.setPassword("validPassword");

        // Mocking behavior
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        // Act
        User resultUser = logInService.getUser(testUser);

        // Assert
        assertEquals(testUser, resultUser);
        verify(userRepository).findByUsername("validUser");
    }

    @Test
    public void testGetUserWithInvalidCredentials() {
        // Arrange
        User testUser = new User();
        testUser.setUsername("validUser");
        testUser.setPassword("invalidPassword");

        // Mocking behavior
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(testUser));
        verify(userRepository).findByUsername("validUser");
    }

    @Test
    public void testGetUserWithNullUser() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(null));
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    public void testGetUserWithNullUserAndNullPassword() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> logInService.getUser(null));
        verify(userRepository, never()).findByUsername(anyString());
    }
}
