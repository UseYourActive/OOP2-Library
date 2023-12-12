package com.library.backend.operations.processors;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.contracts.CreateReaderOperation;
import com.library.backend.operations.requests.CreateReaderRequest;
import com.library.backend.operations.responses.CreateReaderResponse;
import com.library.database.entities.User;
import com.library.database.enums.Role;
import com.library.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class CreateUserOperationProcessor implements CreateReaderOperation {
    private final UserRepository userRepository;
    private final CreateUserResponseConverter converter;

    @Override
    public CreateReaderResponse process(final CreateReaderRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String lastName = request.getLastName();
        String middleName = request.getMiddleName();
        String firstName = request.getFirstName();

        BCrypt.Hasher passwordEncryptor = BCrypt.with(BCrypt.Version.VERSION_2A);

        String hashedPassword = Arrays.toString(passwordEncryptor.hash(12, password.toCharArray()));

        User user = User.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .username(username)
                .password(hashedPassword)
                .email(email)
                .role(Role.READER)
                .build();

        CreateReaderResponse response;

        if (userRepository.save(user)) {
            response = converter.convert(user);
        } else {
            userRepository.close();
            throw new RuntimeException("Critical error");
        }

        userRepository.close();
        return response;
    }
}