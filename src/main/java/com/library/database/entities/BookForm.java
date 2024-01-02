package com.library.database.entities;

import com.library.database.enums.BookFormStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_forms")
public class BookForm implements DBEntity{
    @Id
    @Column(name = "form_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Book> books;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Reader reader;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private BookFormStatus status;

    @Column(name = "date_of_creation",nullable = false,unique = true)
    private LocalDateTime dateOfCreation;

    @Column(name = "date_of_expiration",nullable = false)
    private LocalDateTime expirationDate;

    @Override
    public String toString() {
        return reader.getFirstName()+" "+status+" "+ books.stream().map(Book::toString);
    }

    public boolean isOverdue(){
        return dateOfCreation.isAfter(expirationDate);
    }

    public boolean isPresent(){
        return this.status.equals(BookFormStatus.IN_USE);
    }
}
