package com.library.backend.operations.processors;

import com.library.backend.operations.processors.contracts.LogInOperation;
import com.library.backend.operations.requests.LogInRequest;
import com.library.backend.operations.responses.LogInResponse;
import com.library.database.entities.Reader;
import com.library.database.entities.base.User;
import com.library.database.repositories.ReaderRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LogInOperationProcessor implements LogInOperation {

    private final ReaderRepository readerRepository;

    @Override
    public LogInResponse process(LogInRequest logInRequest) {
        LogInResponse logInResponse = LogInResponse.builder().build();
        try {
            Reader reader = readerRepository.findByUsername(logInRequest.getUsername());
            logInResponse.setEmail(reader.getEmail());
            logInResponse.setFirstName(reader.getFirstName());
            logInResponse.setLastName(reader.getLastName());
            //logInResponse.setRole(reader.getRole().getRole());
            logInResponse.setUsername(reader.getUsername());

        } catch (Exception e) {
            logInResponse.setE(e);
        }
        return logInResponse;
    }
}
