package com.library.backend.operations.responses;

import com.library.database.entities.Author;
import lombok.*;

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
