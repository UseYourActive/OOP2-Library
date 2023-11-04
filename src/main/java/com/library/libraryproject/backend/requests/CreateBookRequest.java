package com.library.libraryproject.backend.requests;

import com.library.libraryproject.backend.annotations.ISBN;
import com.library.libraryproject.backend.operations.base.OperationInput;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookRequest implements OperationInput {
    private String title;
    private String resume;
    private String author;

    @ISBN
    private String isbn;

    @com.library.libraryproject.backend.annotations.Genre
    private String genre;
}
