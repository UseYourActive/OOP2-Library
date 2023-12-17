package com.library.database.entities;

import com.library.database.enums.BookFormStatus;
import com.library.database.enums.Rating;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "readers")
@AllArgsConstructor
public class Reader {
    @Id
    @Column(name = "reader_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 16)
    private String firstName;

    @Column(name = "middle_name", length = 16)
    private String middleName;

    @Column(name = "last_name", length = 16)
    private String lastName;

    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    @Setter
    @Column(name = "email", length = 32, unique = true)
    private String email;

    @Column(name = "book_form_status")
    @Enumerated(EnumType.STRING)
    private BookFormStatus bookFormStatus;

    @Column(name = "reader_rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;
}
