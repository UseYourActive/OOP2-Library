package com.library.backend.operations.processors;

import com.library.backend.operations.processors.contracts.LogInOperation;
import com.library.backend.operations.requests.LogInRequest;
import com.library.backend.operations.responses.LogInResponse;
import com.library.database.entities.User;
import com.library.database.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LogInOperationProcessor implements LogInOperation {

    private final UserRepository userRepository;

    @Override
    public LogInResponse process(LogInRequest logInRequest) {
        LogInResponse logInResponse=LogInResponse.builder().build();
        try{
            User user= userRepository.findByUsername(logInRequest.getUsername());
            logInResponse.setEmail(user.getEmail());
            logInResponse.setFirstName(user.getFirstName());
            logInResponse.setLastName(user.getLastName());
            logInResponse.setRole(user.getRole().getRole());
            logInResponse.setUsername(user.getUsername());

        }catch (Exception e){
            logInResponse.setE(e);
        }
        return logInResponse;
    }
}
