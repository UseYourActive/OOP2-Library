package com.library.libraryproject.backend.responses;

import com.library.libraryproject.backend.operations.base.OperationOutput;
import com.library.libraryproject.database.entities.Author;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CreateBookResponse implements OperationOutput {
    private String bookId;
    private String title;
    private String resume;
    private Author author;
    private String isbn;
    private String genre;
}
