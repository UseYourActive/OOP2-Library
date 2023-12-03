package com.project.backend.operations.requests;

import com.project.backend.annotations.GenreValidation;
import com.project.backend.annotations.ISBNValidation;
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
