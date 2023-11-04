package com.library.libraryproject.backend.processors;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.libraryproject.backend.mappers.CreateUserResponseConverter;
import com.library.libraryproject.backend.operations.CreateUserOperation;
import com.library.libraryproject.backend.requests.CreateUserRequest;
import com.library.libraryproject.backend.responses.CreateUserResponse;
import com.library.libraryproject.database.entities.User;
import com.library.libraryproject.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserOperationProcessor implements CreateUserOperation {
    private final UserRepository userRepository;
    private final CreateUserResponseConverter converter;

    @Override
    public CreateUserResponse process(final CreateUserRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();
        String phone = request.getPhoneNumber();
        String password = request.getPassword();
        String lastName = request.getLastName();
        String firstName = request.getFirstName();
        log.info("Received CreateUserRequest for username: {}", username);

        BCrypt.Hasher passwordEncryptor = BCrypt.with(BCrypt.Version.VERSION_2A);
        log.info("Password hashing for user: {}", username);

        String hashedPassword = Arrays.toString(passwordEncryptor.hash(12, password.toCharArray()));
        log.info("Password hashed for user: {}", username);

        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(hashedPassword)
                .username(username)
                .phone(phone)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User {} saved in the database", username);

        CreateUserResponse response = converter.convert(savedUser);
        log.info("CreateUserResponse generated for user: {}", username);

        return response;
    }
}
