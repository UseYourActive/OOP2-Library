package com.library.libraryproject.backend.mappers;

import com.library.libraryproject.backend.responses.CreateUserResponse;
import com.library.libraryproject.database.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserResponseConverter implements Converter<User, CreateUserResponse> {
    @Override
    public CreateUserResponse convert(User source) {
        return CreateUserResponse.builder()
                .userId(String.valueOf(source.getId()))
                .phone(source.getPhone())
                .email(source.getEmail())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .password(source.getPassword())            // sensitive info
                .role(String.valueOf(source.getRole()))
                .username(source.getUsername())
                .build();
    }
}
