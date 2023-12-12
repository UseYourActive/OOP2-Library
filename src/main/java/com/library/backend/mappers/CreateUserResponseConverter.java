package com.library.backend.mappers;


import com.library.backend.operations.responses.CreateReaderResponse;
import com.library.database.entities.User;


public class CreateUserResponseConverter implements Converter<User, CreateReaderResponse> {
    @Override
    public CreateReaderResponse convert(User source) {
        return CreateReaderResponse.builder()
                .userId(String.valueOf(source.getId()))
//                .email(source.getEmail())
//                .firstName(source.getFirstName())
//                .lastName(source.getLastName())
//                .role(String.valueOf(source.getRole()))
                .username(source.getUsername())
                .build();
    }
}
