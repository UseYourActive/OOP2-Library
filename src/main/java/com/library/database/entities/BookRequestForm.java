package com.library.database.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "forms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookRequestForm {

    @Id
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    private Reader reader;

    //@OneToOne(mappedBy = "bookRequestForm", orphanRemoval = true)
    private Book book;
}
