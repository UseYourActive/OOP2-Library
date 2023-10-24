package com.library.responses;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SampleResponse {
    private UUID BookId;
    private String title;
    private String resume;
//    private List<Author> author;
    private String isbn;
//    private Genre genre;
}
