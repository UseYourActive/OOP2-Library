package com.library.backend.mappers;


import com.library.backend.operations.responses.CreateUserResponse;
import com.library.database.entities.User;


public class CreateUserResponseConverter implements Converter<User, CreateUserResponse> {
    @Override
    public CreateUserResponse convert(User source) {
        return CreateUserResponse.builder()
                .userId(String.valueOf(source.getId()))
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .role(String.valueOf(source.getRole()))
                .username(source.getUsername())
                .build();
    }
}