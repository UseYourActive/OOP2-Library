package com.library.backend.operations.processors;

import com.library.backend.operations.processors.contracts.LogInOperation;
import com.library.backend.operations.requests.LogInRequest;
import com.library.backend.operations.responses.LogInResponse;
import com.library.backend.operations.responses.Role;
import com.library.database.entities.Admin;
import com.library.database.entities.Operator;
import com.library.database.entities.Reader;
import com.library.database.entities.base.User;
import com.library.database.repositories.AdminRepository;
import com.library.database.repositories.OperatorRepository;
import com.library.database.repositories.ReaderRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class LogInOperationProcessor implements LogInOperation {

    private final ReaderRepository readerRepository;
    private final OperatorRepository operatorRepository;
    private final AdminRepository adminRepository;

    @Override
    public LogInResponse process(LogInRequest logInRequest) throws Exception {

        User user=getUser(logInRequest);

        return getLogInResponse(user);
    }

    private User getUser(LogInRequest logInRequest) throws Exception {

        User user = readerRepository.findByUsername(logInRequest.getUsername());

        if (user == null)
            user = operatorRepository.findByUsername(logInRequest.getUsername());

        if (user == null) {
            user = adminRepository.findByUsername(logInRequest.getUsername());
        }
        return user;
    }

    private LogInResponse getLogInResponse(User user){
        LogInResponse logInResponse = LogInResponse.builder().build();

        if(user instanceof Reader){
            logInResponse.setEmail(((Reader)user).getEmail());
            logInResponse.setFirstName(((Reader)user).getFirstName());
            logInResponse.setLastName(((Reader)user).getLastName());
            logInResponse.setRole(Role.READER);
        }

        if(user instanceof Operator){
            logInResponse.setRole(Role.OPERATOR);
        }

        if(user instanceof Admin){
            logInResponse.setRole(Role.ADMIN);
        }

        logInResponse.setUsername(user.getUsername());

        return logInResponse;
    }
}
