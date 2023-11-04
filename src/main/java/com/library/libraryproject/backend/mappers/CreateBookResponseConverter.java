package com.library.libraryproject.backend.mappers;

import com.library.libraryproject.backend.responses.CreateBookResponse;
import com.library.libraryproject.database.entities.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateBookResponseConverter implements Converter<Book, CreateBookResponse> {

    @Override
    public CreateBookResponse convert(Book source) {
        return CreateBookResponse.builder()
                .bookId(String.valueOf(source.getId()))
                .author(source.getAuthor())
                .title(source.getTitle())
                .isbn(source.getIsbn())
                .genre(String.valueOf(source.getGenre()))
                .resume(source.getResume())
                .build();
    }
}
