package com.library.backend.operations.requests;

import com.library.backend.annotations.GenreValidation;
import com.library.backend.annotations.ISBNValidation;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateBookRequest implements Request {
    private String title;
    private String resume;
    private String author;

    @ISBNValidation
    private String isbn;

    @GenreValidation
    private String genre;
}
