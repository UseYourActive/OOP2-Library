package com.project.backend.operations.processors;

import com.project.backend.operations.processors.contracts.LogInOperation;
import com.project.backend.operations.requests.LogInRequest;
import com.project.backend.operations.responses.LogInResponse;
import com.project.database.entities.User;
import com.project.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LogInOperationProcessor implements LogInOperation {

    private final UserRepository userRepository;

    @Override
    public LogInResponse process(LogInRequest logInRequest) {
        LogInResponse logInResponse = LogInResponse.builder().build();
        try {
            User user = userRepository.findByUsername(logInRequest.getUsername());
            logInResponse.setEmail(user.getEmail());
            logInResponse.setFirstName(user.getFirstName());
            logInResponse.setLastName(user.getLastName());
            logInResponse.setRole(user.getRole().getRole());
            logInResponse.setUsername(user.getUsername());

        } catch (Exception e) {
            logInResponse.setE(e);
        }
        return logInResponse;
    }
}
