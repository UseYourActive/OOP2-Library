package com.library.processor;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.entities.Role;
import com.library.entities.User;
import com.library.operation.CreateNewUserOperation;
import com.library.repositories.UserRepository;
import com.library.requests.CreateNewUserRequest;
import com.library.responses.CreateNewUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterNewUserOperationProcessor implements CreateNewUserOperation {
    private final UserRepository userRepository;
//    private final CreateNewUserMapper createNewUserMapper;

    @Override
    public CreateNewUserResponse process(final CreateNewUserRequest input) {
        log.info("Processing a new user registration.");

        String username = input.getUsername();
        String firstName = input.getFirstName();
        String lastName = input.getLastName();
        String email = input.getEmail();
        String phone = input.getPhone();
        String password = input.getPassword();

        log.debug("Received user data: username={}, firstName={}, lastName={}, email={}, phone={}, password={}",
                username, firstName, lastName, email, phone, "********");

        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        log.debug("Password hashed.");

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(Role.CLIENT)
                .phone(phone)
                .username(username)
                .password(hashedPassword)
                .build();
        //User user = createNewUserMapper.INSTANCE.mapToEntity(input);

        User savedUser = userRepository.save(user);

        log.info("User saved with ID: {}", savedUser.getId());

        boolean isPasswordValid = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;

        log.info("Password verification result: {}", isPasswordValid);

        CreateNewUserResponse response = CreateNewUserResponse.builder()
                .id(String.valueOf(savedUser.getId()))
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .role(String.valueOf(savedUser.getRole()))
                .phone(savedUser.getPhone())
                .password(savedUser.getPassword())
                .username(savedUser.getUsername())
                .build();

        log.info("User registration completed.");

        return response;
    }
}
