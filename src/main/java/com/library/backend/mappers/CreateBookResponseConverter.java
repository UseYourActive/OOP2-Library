package com.library.backend.mappers;


import com.library.backend.operations.responses.CreateBookResponse;
import com.library.database.entities.Book;


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
