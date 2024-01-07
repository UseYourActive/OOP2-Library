package services;

import com.library.backend.exception.users.UserNotFoundException;
import com.library.backend.services.LogInService;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testGetUserUserNotFound() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("nonExistentUser");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> logInService.getUser("nonExistentUser", null));
        verify(userRepository).findByUsername("nonExistentUser");
    }

    @Test
    public void testGetUserWithNullUsername() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> logInService.getUser(null, null));
        verify(userRepository).findByUsername(isNull());
    }


    @Test
    public void testGetUserWithEmptyUsername() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> logInService.getUser("", null));
        verify(userRepository).findByUsername("");
    }

    @Test
    public void testGetUserWithWhitespaceUsername() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("   ");

        // Mocking the behavior of UserRepository
        when(userRepository.findByUsername("   ")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> logInService.getUser("   ", null));
        verify(userRepository).findByUsername("   ");
    }

    @Test
    public void testGetUserWithNonexistentUsername() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("nonexistentUser");

        // Mocking behavior
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> logInService.getUser("nonexistentUser", null));
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
        User resultUser = logInService.getUser("validUser", "validPassword");

        // Assert
        assertEquals(testUser, resultUser);
        verify(userRepository).findByUsername("validUser");
    }

    @Test
    public void testGetUserWithInvalidCredentials() throws UserNotFoundException {
        // Arrange
        User testUser = new User();
        testUser.setUsername("validUser");
        testUser.setPassword("invalidPassword");

        // Mocking behavior
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> logInService.getUser("validUser", "invalidPassword"));
        verify(userRepository).findByUsername("validUser");
    }
}
