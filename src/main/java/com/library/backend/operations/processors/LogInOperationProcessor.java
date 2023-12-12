package com.library.backend.operations.processors;

import com.library.backend.exception.ProcessException;
import com.library.backend.operations.processors.contracts.LogInOperation;
import com.library.backend.operations.requests.LogInRequest;
import com.library.backend.operations.responses.LogInResponse;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class LogInOperationProcessor implements LogInOperation {
    private static final Logger logger = LoggerFactory.getLogger(LogInOperationProcessor.class);

    private final UserRepository userRepository;

    @Override
    public LogInResponse process(final LogInRequest request) throws ProcessException {
        logger.info("Processing login request for username: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername());

        logger.info("Login request processed successfully for username: {}", request.getUsername());

        return LogInResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .userId(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .role(user.getRole())
                .build();
    }
}
