package com.library.processors;

import com.library.entities.Author;
import com.library.entities.Book;
import com.library.mappers.CreateNewAuthorMapper;
import com.library.operations.CreateNewAuthorOperation;
import com.library.repositories.AuthorRepository;
import com.library.repositories.BookRepository;
import com.library.requests.CreateNewAuthorRequest;
import com.library.responses.CreateNewAuthorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class CreateNewAuthorOperationProcessor implements CreateNewAuthorOperation {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CreateNewAuthorMapper createNewAuthorMapper;

    @Override
    public CreateNewAuthorResponse process(final CreateNewAuthorRequest createNewAuthorRequest) {
        log.info("Processing a new author creation request.");

        String firstName = createNewAuthorRequest.getFirstName();
        String secondName = createNewAuthorRequest.getSecondName();
        String lastName = createNewAuthorRequest.getLastName();
        String country = createNewAuthorRequest.getCountry();
        String dateOfBirth = createNewAuthorRequest.getDateOfBirth();
        List<String> books = createNewAuthorRequest.getBookIds();

        List<UUID> listOfBooks = books.stream()
                .map(UUID::fromString)
                .toList();

        log.debug("Books to be associated with the author: {}", listOfBooks);

        List<Book> allFoundBooksById = bookRepository.findAllById(listOfBooks);

        Author author = Author.builder()
                .firstName(firstName)
                .secondName(secondName)
                .lastName(lastName)
                .country(country)
                .dateOfBirth(Timestamp.valueOf(dateOfBirth))
                .books(new ArrayList<>(allFoundBooksById))
                .build();

        Author savedAuthor = authorRepository.save(author);

        log.info("Author created with ID: {}", savedAuthor.getId());

        return CreateNewAuthorResponse.builder()
                .firstName(savedAuthor.getFirstName())
                .secondName(savedAuthor.getSecondName())
                .lastName(savedAuthor.getLastName())
                .country(savedAuthor.getCountry())
                .dateOfBirth(String.valueOf(savedAuthor.getDateOfBirth()))
                .books(savedAuthor.getBooks().stream()
                        .map(String::valueOf)
                        .toList())
                .build();
    }
}
