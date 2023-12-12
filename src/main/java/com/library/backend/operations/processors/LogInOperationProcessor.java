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

    private final ReaderRepository readerRepository;
    private final OperatorRepository operatorRepository;
    private final AdminRepository adminRepository;

    @Override
    public LogInResponse process(LogInRequest logInRequest) throws Exception {

        User user = getUser(logInRequest);

        if (user == null)
            throw new UserNotFoundException("User not found");

        return getLogInResponse(user);
    }

    private User getUser(LogInRequest logInRequest) {
        User user = null;

        try {
            user = readerRepository.findByUsername(logInRequest.getUsername());
        } catch (ReaderNotFoundException ignored) {
        }

        try {
            user = operatorRepository.findByUsername(logInRequest.getUsername());
        } catch (OperatorNotFoundException ignored) {
        }

        try {
            user = adminRepository.findByUsername(logInRequest.getUsername());
        } catch (AdminNotFoundException ignored) {
        }

        return user;
    }

    private LogInResponse getLogInResponse(User user) {
        LogInResponse logInResponse = LogInResponse.builder().build();

        if (user instanceof Reader) {
            logInResponse.setEmail(((Reader) user).getEmail());
            logInResponse.setFirstName(((Reader) user).getFirstName());
            logInResponse.setLastName(((Reader) user).getLastName());
            logInResponse.setRole(Role.READER);
        }

        if (user instanceof Operator) {
            logInResponse.setRole(Role.OPERATOR);
        }

        if (user instanceof Admin) {
            logInResponse.setRole(Role.ADMIN);
        }

        logInResponse.setUsername(user.getUsername());

        return logInResponse;
    }
}
