package com.library.libraryproject.backend.processors;

import com.library.libraryproject.backend.mappers.CreateBookResponseConverter;
import com.library.libraryproject.backend.operations.CreateBookOperation;
import com.library.libraryproject.backend.requests.CreateBookRequest;
import com.library.libraryproject.backend.responses.CreateBookResponse;
import com.library.libraryproject.database.entities.Author;
import com.library.libraryproject.database.entities.Book;
import com.library.libraryproject.database.repositories.AuthorRepository;
import com.library.libraryproject.database.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateBookOperationProcessor implements CreateBookOperation {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CreateBookResponseConverter converter;

    @Override
    public CreateBookResponse process(final CreateBookRequest request) {
        log.info("Processing CreateBookRequest: {}", request);

        String title = request.getTitle();
        String isbn = request.getIsbn();
        String author = request.getAuthor();
        String genre = request.getGenre();
        String resume = request.getResume();
        log.debug("Received values: title={}, isbn={}, author={}, genre={}, resume={}", title, isbn, author, genre, resume);

        //Author author = authorRepository.find

        Book book = Book.builder()
//                .author(author)
//                .genre(genre)
                .isbn(isbn)
                .title(title)
                .resume(resume)
                .build();
        log.debug("Created Book object: {}", book);

        Book savedBook = bookRepository.save(book);
        log.info("Saved Book: {}", savedBook);

        CreateBookResponse response = converter.convert(savedBook);
        log.info("Created CreateBookResponse: {}", response);

        return response;
    }
}
