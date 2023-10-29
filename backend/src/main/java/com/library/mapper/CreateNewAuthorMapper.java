package com.library.mapper;

import com.library.entities.Author;
import com.library.requests.CreateNewAuthorRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CreateNewAuthorMapper {
    CreateNewAuthorMapper INSTANCE = Mappers.getMapper(CreateNewAuthorMapper.class);

    Author mapToEntity(CreateNewAuthorRequest input);
}
