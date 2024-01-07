package services.admin;

import com.library.backend.services.admin.AdministratorOperatorsService;
import com.library.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public class AdministratorOperatorsServiceTest {
    private UserRepository userRepository;
    private AdministratorOperatorsService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        service = new AdministratorOperatorsService(userRepository);
    }
}
