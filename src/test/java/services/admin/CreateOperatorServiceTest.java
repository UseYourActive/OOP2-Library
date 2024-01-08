package services.admin;

import com.library.backend.exception.IncorrectInputException;
import com.library.backend.exception.UserExistException;
import com.library.backend.exception.users.UserNotFoundException;
import com.library.backend.services.admin.CreateOperatorService;
import com.library.backend.validators.StrongPasswordValidator;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateOperatorServiceTest {
    private CreateOperatorService service;
    private UserRepository userRepository;
    private StrongPasswordValidator strongPasswordValidator;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        service = new CreateOperatorService(userRepository);
        strongPasswordValidator = new StrongPasswordValidator();
        service.setStrongPasswordValidator(strongPasswordValidator);
    }

    @Test
    void createOperator_UserExistsException() throws UserExistException, IncorrectInputException, UserNotFoundException {
        // Arrange
        when(userRepository.findByUsername("existingOperator")).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserExistException.class, () ->
                service.createOperator("existingOperator", "StrongP@ssword1", "StrongP@ssword1"));

        // Verify that userRepository.findByUsername is called
        verify(userRepository, times(1)).findByUsername("existingOperator");
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createOperator_IncorrectInputException_EmptyFields() {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator("", "", ""));
    }

    @Test
    void createOperator_IncorrectInputException_PasswordsDoNotMatch() {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator("newOperator", "password1", "password2"));
    }

    @Test
    void createOperator_IncorrectInputException_WeakPassword() {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator("newOperator", "weakpassword", "weakpassword"));
    }

    @Test
    void createOperator_UsernameIsNull() throws UserNotFoundException {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator(null, "StrongP@ssword1", "StrongP@ssword1"));
        // Verify that userRepository.findByUsername is never called
        verify(userRepository, never()).findByUsername(anyString());
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createOperator_UsernameAlreadyExists() throws UserExistException, IncorrectInputException, UserNotFoundException {
        // Arrange
        when(userRepository.findByUsername("existingOperator")).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserExistException.class, () ->
                service.createOperator("existingOperator", "StrongP@ssword1", "StrongP@ssword1"));

        // Verify that userRepository.findByUsername is called
        verify(userRepository).findByUsername("existingOperator");
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createOperator_PasswordIsNull() throws UserNotFoundException {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator("newOperator", null, null));
        // Verify that userRepository.findByUsername is never called
        verify(userRepository, never()).findByUsername(anyString());
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createOperator_PasswordsAreWhitespace() throws UserNotFoundException {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator("newOperator", "  ", "  "));
        // Verify that userRepository.findByUsername is never called
        verify(userRepository, never()).findByUsername(anyString());
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createOperator_PasswordTooShort() throws UserNotFoundException {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () ->
                service.createOperator("newOperator", "Pwd1", "Pwd1"));
        // Verify that userRepository.findByUsername is never called
        verify(userRepository, never()).findByUsername(anyString());
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createOperator_PasswordTooLong() throws UserNotFoundException {
        // Act & Assert
        assertThrows(IncorrectInputException.class, () -> {
            // Generate a password longer than the allowed length
            StringBuilder longPassword = new StringBuilder();
            for (int i = 0; i < 101; i++) {
                longPassword.append("a");
            }
            service.createOperator("newOperator", longPassword.toString(), longPassword.toString());
        });
        // Verify that userRepository.findByUsername is never called
        verify(userRepository, never()).findByUsername(anyString());
        // Verify that userRepository.save is never called
        verify(userRepository, never()).save(any(User.class));
    }
}
