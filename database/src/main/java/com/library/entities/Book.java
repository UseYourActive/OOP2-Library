package com.library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Getter
//@Setter
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "resume", nullable = false)
    private String resume;

    @ManyToOne
    @JoinColumn(name = "authors", nullable = false)
    private Author author;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
