package repositories;

import com.library.backend.exception.users.UserNotFoundException;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private Session session;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_UserFound_ReturnsUserOptional() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(session.get(User.class, userId)).thenReturn(expectedUser);

        // Act
        Optional<User> result = userRepository.findById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(session).get(User.class, userId);
    }

    @Test
    void findById_UserNotFound_ReturnsEmptyOptional() {
        // Arrange
        Long userId = 1L;

        when(session.get(User.class, userId)).thenReturn(null);

        // Act
        Optional<User> result = userRepository.findById(userId);

        // Assert
        assertTrue(result.isEmpty());
        verify(session).get(User.class, userId);
    }

    @Test
    void findAll_MultipleUsers_ReturnsListOfUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> expectedUsers = Arrays.asList(user1, user2);

        Query<User> query = mock(Query.class);
        when(session.createQuery(eq("SELECT u FROM User u"), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        // Act
        List<User> result = userRepository.findAll();

        // Assert
        assertEquals(expectedUsers.size(), result.size());
        assertTrue(result.containsAll(expectedUsers));
        verify(session).createQuery(eq("SELECT u FROM User u"), eq(User.class));
    }

    @Test
    void findAll_NoUsers_ReturnsEmptyList() {
        // Arrange
        List<User> expectedUsers = Arrays.asList();

        Query<User> query = mock(Query.class);
        when(session.createQuery(eq("SELECT u FROM User u"), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        // Act
        List<User> result = userRepository.findAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(session).createQuery(eq("SELECT u FROM User u"), eq(User.class));
    }

    @Test
    void getById_UserFound_ReturnsUser() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(session.get(User.class, userId)).thenReturn(expectedUser);

        // Act
        User result = userRepository.getById(userId);

        // Assert
        assertEquals(expectedUser, result);
        verify(session).get(User.class, userId);
    }

    @Test
    void getById_UserNotFound_ReturnsNull() {
        // Arrange
        Long userId = 1L;

        when(session.get(User.class, userId)).thenReturn(null);

        // Act
        User result = userRepository.getById(userId);

        // Assert
        assertNull(result);
        verify(session).get(User.class, userId);
    }

    @Test
    void findByUsername_UserFound_ReturnsUserOptional() throws UserNotFoundException {
        // Arrange
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        Query<User> query = mock(Query.class);
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(expectedUser);

        // Act
        Optional<User> result = userRepository.findByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(session).createQuery(anyString(), eq(User.class));
    }
}
