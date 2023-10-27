package com.library.mappers;

import com.library.entities.User;
import com.library.requests.CreateNewUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreateNewUserMapper {
    CreateNewUserMapper INSTANCE = Mappers.getMapper(CreateNewUserMapper.class);

    User mapToEntity(CreateNewUserRequest input);
}
