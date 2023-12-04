package com.library.backend.operations.processors;


import at.favre.lib.crypto.bcrypt.BCrypt;
import com.library.backend.mappers.CreateUserResponseConverter;
import com.library.backend.operations.processors.contracts.CreateReaderOperation;
import com.library.backend.operations.requests.CreateReaderRequest;
import com.library.backend.operations.responses.CreateReaderResponse;
import com.library.database.entities.Reader;
import com.library.database.repositories.ReaderRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class CreateReaderOperationProcessor implements CreateReaderOperation {
    private final ReaderRepository readerRepository;
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

        Reader reader=Reader.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .username(username)
                .password(hashedPassword)
                .email(email)
                .build();

        //User user1= Admin.builder()


//        userRepository.openSession();

        CreateReaderResponse response;

        if (readerRepository.save(reader)) {
            response = converter.convert(reader);
        }
        else {
            readerRepository.close();
            throw new RuntimeException("Critical error");
        }

        readerRepository.close();
        return response;
    }
}
