package repositories;

import com.library.database.entities.Author;
import com.library.database.repositories.AuthorRepository;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorRepositoryTest {

    @Mock
    private Session session;

    @InjectMocks
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long authorId = 1L;
        Author expectedAuthor = new Author();
        expectedAuthor.setId(authorId);
        when(session.get(Author.class, authorId)).thenReturn(expectedAuthor);

        Optional<Author> result = authorRepository.findById(authorId);

        assertEquals(Optional.of(expectedAuthor), result);
        verify(session).get(Author.class, authorId);
    }

    @Test
    void testFindAll() {
        Query<Author> query = mock(Query.class);
        when(session.createQuery("SELECT a FROM Author a", Author.class)).thenReturn(query);

        List<Author> expectedAuthors = Collections.singletonList(new Author());
        when(query.getResultList()).thenReturn(expectedAuthors);

        List<Author> result = authorRepository.findAll();

        assertEquals(expectedAuthors, result);
        verify(session).createQuery("SELECT a FROM Author a", Author.class);
    }

    @Test
    void testGetById() {
        Long authorId = 1L;
        Author expectedAuthor = new Author();
        expectedAuthor.setId(authorId);
        when(session.get(Author.class, authorId)).thenReturn(expectedAuthor);

        Author result = authorRepository.getById(authorId);

        assertEquals(expectedAuthor, result);
        verify(session).get(Author.class, authorId);
    }

    @Test
    void testFindByName() {
        String authorName = "John Doe";
        String queryStr = "SELECT a FROM Author a WHERE a.name = :name";
        Query<Author> query = mock(Query.class);
        when(session.createQuery(queryStr, Author.class)).thenReturn(query);
        when(query.setParameter("name", authorName)).thenReturn(query);

        Author expectedAuthor = new Author();
        when(query.getSingleResult()).thenReturn(expectedAuthor);

        Optional<Author> result = authorRepository.findByName(authorName);

        assertEquals(Optional.of(expectedAuthor), result);
        verify(session).createQuery(queryStr, Author.class);
        verify(query).setParameter("name", authorName);
    }

    @Test
    void testFindAllAuthorsByName() {
        String authorName = "John Doe";
        String queryStr = "SELECT a FROM Author a WHERE a.name = :name";
        Query<Author> query = mock(Query.class);
        when(session.createQuery(queryStr, Author.class)).thenReturn(query);
        when(query.setParameter("name", authorName)).thenReturn(query);

        List<Author> expectedAuthors = Collections.singletonList(new Author());
        when(query.getResultList()).thenReturn(expectedAuthors);

        List<Author> result = authorRepository.findAllAuthorsByName(authorName);

        assertEquals(expectedAuthors, result);
        verify(session).createQuery(queryStr, Author.class);
        verify(query).setParameter("name", authorName);
    }

    @Test
    void testFindAllAuthorsByCountry() {
        String country = "United States";
        String queryStr = "SELECT a FROM Author a WHERE a.country = :country";
        Query<Author> query = mock(Query.class);
        when(session.createQuery(queryStr, Author.class)).thenReturn(query);
        when(query.setParameter("country", country)).thenReturn(query);

        List<Author> expectedAuthors = Collections.singletonList(new Author());
        when(query.getResultList()).thenReturn(expectedAuthors);

        List<Author> result = authorRepository.findAllAuthorsByCountry(country);

        assertEquals(expectedAuthors, result);
        verify(session).createQuery(queryStr, Author.class);
        verify(query).setParameter("country", country);
    }

    @Test
    void testFindByIdNotFound() {
        Long authorId = 1L;
        when(session.get(Author.class, authorId)).thenReturn(null);

        Optional<Author> result = authorRepository.findById(authorId);

        assertFalse(result.isPresent());
        verify(session).get(Author.class, authorId);
    }

    @Test
    void testFindAllNoAuthors() {
        Query<Author> query = mock(Query.class);
        when(session.createQuery("SELECT a FROM Author a", Author.class)).thenReturn(query);

        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Author> result = authorRepository.findAll();

        assertEquals(Collections.emptyList(), result);
        verify(session).createQuery("SELECT a FROM Author a", Author.class);
    }

    @Test
    void testGetByIdNotFound() {
        Long authorId = 1L;
        when(session.get(Author.class, authorId)).thenReturn(null);

        Author result = authorRepository.getById(authorId);

        assertNull(result);
        verify(session).get(Author.class, authorId);
    }

    @Test
    void testFindByNameNotFound() {
        String authorName = "John Doe";
        String queryStr = "SELECT a FROM Author a WHERE a.name = :name";
        Query<Author> query = mock(Query.class);
        when(session.createQuery(queryStr, Author.class)).thenReturn(query);
        when(query.setParameter("name", authorName)).thenReturn(query);

        when(query.getSingleResult()).thenThrow(new NoResultException());

        Optional<Author> result = authorRepository.findByName(authorName);

        assertFalse(result.isPresent());
        verify(session).createQuery(queryStr, Author.class);
        verify(query).setParameter("name", authorName);
    }

    @Test
    void testFindAllAuthorsByNameNoAuthors() {
        String authorName = "John Doe";
        String queryStr = "SELECT a FROM Author a WHERE a.name = :name";
        Query<Author> query = mock(Query.class);
        when(session.createQuery(queryStr, Author.class)).thenReturn(query);
        when(query.setParameter("name", authorName)).thenReturn(query);

        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Author> result = authorRepository.findAllAuthorsByName(authorName);

        assertEquals(Collections.emptyList(), result);
        verify(session).createQuery(queryStr, Author.class);
        verify(query).setParameter("name", authorName);
    }

    @Test
    void testFindAllAuthorsByCountryNoAuthors() {
        String country = "United States";
        String queryStr = "SELECT a FROM Author a WHERE a.country = :country";
        Query<Author> query = mock(Query.class);
        when(session.createQuery(queryStr, Author.class)).thenReturn(query);
        when(query.setParameter("country", country)).thenReturn(query);

        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Author> result = authorRepository.findAllAuthorsByCountry(country);

        assertEquals(Collections.emptyList(), result);
        verify(session).createQuery(queryStr, Author.class);
        verify(query).setParameter("country", country);
    }
}
