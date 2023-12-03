package com.project.backend.operations.processors;


import com.project.backend.mappers.CreateBookResponseConverter;
import com.project.backend.operations.processors.contracts.CreateBookOperation;
import com.project.backend.operations.requests.CreateBookRequest;
import com.project.backend.operations.responses.CreateBookResponse;
import com.project.database.entities.Author;
import com.project.database.entities.Book;
import com.project.database.entities.Genre;
import com.project.database.repositories.AuthorRepository;
import com.project.database.repositories.BookRepository;
import com.project.database.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateBookOperationProcessor implements CreateBookOperation {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final CreateBookResponseConverter converter;

    @Override
    public CreateBookResponse process(final CreateBookRequest request) {
        //log.info("Processing CreateBookRequest: {}", request);

        String title = request.getTitle();
        String isbn = request.getIsbn();
        String authorName = request.getAuthor();
        String genreName = request.getGenre();
        String resume = request.getResume();
        //  log.debug("Received values: title={}, isbn={}, author={}, genre={}, resume={}", title, isbn, author, genre, resume);

        Author author = authorRepository.getByName(authorName);
        Genre genre = genreRepository.getByName(genreName);

        Book book = Book.builder()
                .author(author)
                .genre(genre)
                .isbn(isbn)
                .title(title)
                .resume(resume)
                .build();
        //log.debug("Created Book object: {}", book);

        if (bookRepository.save(book)) {
            return converter.convert(book);
        }

        //Book savedBook = bookRepository.save(book);
        //log.info("Saved Book: {}", savedBook);

        //CreateBookResponse response = converter.convert(savedBook);
        //log.info("Created CreateBookResponse: {}", response);

        return null;
    }
}
