package com.project.backend.operations.responses;

import com.project.database.entities.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
public class CreateBookResponse implements Response {
    private String bookId;
    private String title;
    private String resume;
    private Author author;
    private String isbn;
    private String genre;
}
