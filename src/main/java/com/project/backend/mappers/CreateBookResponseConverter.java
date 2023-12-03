package com.project.backend.mappers;


import com.project.backend.operations.responses.CreateBookResponse;
import com.project.database.entities.Book;


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
