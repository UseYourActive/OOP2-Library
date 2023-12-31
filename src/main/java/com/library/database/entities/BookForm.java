package com.library.database.entities;

import com.library.database.enums.BookFormStatus;
import com.library.database.enums.ExpirationPolicy;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @OneToMany()
    private List<Book> books;

    @OneToOne()
    private Reader reader;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private BookFormStatus status;

    @Column(name = "date_of_creation",nullable = false,unique = true)
    private LocalDateTime dateOfCreation;

    @Column(name = "expiration",nullable = false)
    private ExpirationPolicy expirationPolicy;

    public boolean isExpired(){
        if(expirationPolicy.equals(ExpirationPolicy.HOURS_24))
            return dateOfCreation.plusHours(expirationPolicy.getValue()).isAfter(LocalDateTime.now());
        else
            return dateOfCreation.plusMonths(expirationPolicy.getValue()).isAfter(LocalDateTime.now());
    }
}
