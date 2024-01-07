package services.admin;

import com.library.backend.services.admin.CreateOperatorService;
import com.library.backend.validators.StrongPasswordValidator;
import com.library.database.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

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
}
