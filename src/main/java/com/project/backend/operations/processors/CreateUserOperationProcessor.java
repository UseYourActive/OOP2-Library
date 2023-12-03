package com.project.backend.operations.processors;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.project.backend.mappers.CreateUserResponseConverter;
import com.project.backend.operations.processors.contracts.CreateUserOperation;
import com.project.backend.operations.requests.CreateUserRequest;
import com.project.backend.operations.responses.CreateUserResponse;
import com.project.database.entities.Role;
import com.project.database.entities.User;
import com.project.database.repositories.UserRepository;
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
                .email(email)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .password(hashedPassword)
                .username(username)
                .role(Role.CLIENT)
                .build();

        userRepository.openSession();

        CreateUserResponse response = null;

        if (userRepository.save(user)) {
            response = converter.convert(user);
        }

        userRepository.close();

        return response;
    }
}
