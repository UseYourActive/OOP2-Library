package com.library.backend.operations.processors;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.contracts.CreateUserOperation;
import com.library.backend.operations.requests.CreateUserRequest;
import com.library.backend.operations.responses.CreateUserResponse;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class CreateUserOperationProcessor implements CreateUserOperation {
    private final UserRepository userRepository;
    private final CreateUserResponseConverter converter;

    @Override
    public CreateUserResponse process(final CreateUserRequest request) {
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        String lastName = request.getLastName();
        String middleName = request.getMiddleName();
        String firstName = request.getFirstName();

        BCrypt.Hasher passwordEncryptor = BCrypt.with(BCrypt.Version.VERSION_2A);

        String hashedPassword = Arrays.toString(passwordEncryptor.hash(12, password.toCharArray()));

        User user = User.builder()
                .password(hashedPassword)
                .username(username)
                .build();

//        userRepository.openSession();

        CreateUserResponse response;

        if (userRepository.save(user)) {
            response = converter.convert(user);
        }
        else {
            userRepository.close();
            throw new RuntimeException("Critical error");
        }

        userRepository.close();
        return response;
    }
}
