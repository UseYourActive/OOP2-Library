package services.admin;

import com.library.backend.exception.searchengine.SearchEngineException;
import com.library.backend.services.admin.AdministratorOperatorsService;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.database.repositories.UserRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdministratorOperatorsServiceTest {
    private UserRepository userRepository;
    private AdministratorOperatorsService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        service = new AdministratorOperatorsService(userRepository);
    }

    @Test
    void testSearchUser() throws SearchEngineException, SearchEngineException {
        // Arrange
        String searchString = "John";
        List<User> users = Arrays.asList(
                new User(1L, "JohnDoe", "password", Role.OPERATOR, Collections.emptyList()),
                new User(2L, "JaneDoe", "password", Role.OPERATOR, Collections.emptyList())
        );
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> searchResult = service.searchUser(searchString).stream().toList();

        // Assert
        assertNotNull(searchResult);
        assertEquals(1, searchResult.size());
        assertEquals("JohnDoe", searchResult.get(0).getUsername());
    }

    @Test
    void testRemoveOperator() throws Exception {
        // Arrange
        User operator = new User(1L, "JohnDoe", "password", Role.OPERATOR, Collections.emptyList());

        // Act
        service.removeOperator(operator);

        // Assert
        verify(userRepository).delete(operator);
    }

    @Test
    void testRemoveAdministrator() {
        // Arrange
        User administrator = new User(1L, "AdminUser", "password", Role.ADMIN, Collections.emptyList());

        // Act and Assert
        assertThrows(Exception.class, () -> service.removeOperator(administrator));
        verify(userRepository, never()).delete(administrator);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                new User(1L, "JohnDoe", "password", Role.OPERATOR, Collections.emptyList()),
                new User(2L, "JaneDoe", "password", Role.OPERATOR, Collections.emptyList())
        );
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> allUsers = service.getAllUsers();

        // Assert
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertEquals("JohnDoe", allUsers.get(0).getUsername());
        assertEquals("JaneDoe", allUsers.get(1).getUsername());
    }

    @Test
    void testSearchUserNoResults() throws SearchEngineException {
        // Arrange
        String searchString = "NonExistingUser";
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<User> searchResult = service.searchUser(searchString).stream().toList();

        // Assert
        assertNotNull(searchResult);
        assertTrue(searchResult.isEmpty());
    }

    @Test
    void testRemoveNullOperator() {
        // Arrange
        User nullOperator = null;

        // Act and Assert
        assertThrows(Exception.class, () -> service.removeOperator(nullOperator));
        verify(userRepository, never()).delete(nullOperator);
    }
}
