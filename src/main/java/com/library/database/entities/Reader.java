package com.library.database.entities;

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

    @OneToOne
    @JoinColumn(name = "book_form")
    private BookForm bookForm;

    @Column(name = "reader_rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Override
    public String toString() {
        return String.format("Id: %d, First Name: %s, Middle Name: %s, Last Name: %s, Phone Number: %s, Email: %s, Book Form Id: %d, Rating: %s",
                id, firstName, middleName, lastName, phoneNumber, email, bookForm.getId(), rating.getDisplayValue());
    }
}
