package com.library.database.entities;

import com.library.database.enums.ReaderRating;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * The {@code Reader} class represents a library reader with personal information, contact details, and
 * a list of associated book forms. It also includes a reader rating.
 * <p>
 * This class is annotated with JPA annotations for entity mapping and is designed to be used with a
 * relational database.
 *
 * @see DBEntity
 * @see Table
 * @see Column
 * @see GeneratedValue
 * @see GenerationType
 * @see Enumerated
 * @see OneToMany
 * @see JoinColumn
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.Builder
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "readers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"first_name", "middle_name", "last_name"})
})
public class Reader implements DBEntity{
    /**
     * The unique identifier for the reader.
     */
    @Id
    @Column(name = "reader_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the reader.
     */
    @Column(name = "first_name", length = 16)
    private String firstName;

    /**
     * The middle name of the reader.
     */
    @Column(name = "middle_name", length = 16)
    private String middleName;

    /**
     * The last name of the reader.
     */
    @Column(name = "last_name", length = 16)
    private String lastName;

    /**
     * The phone number of the reader.
     */
    @Column(name = "phone_number", length = 16)
    private String phoneNumber;

    /**
     * The email address of the reader (unique constraint).
     */
    @Setter
    @Column(name = "email", length = 32, unique = true)
    private String email;

    /**
     * The list of book forms associated with the reader.
     */
    @OneToMany(mappedBy = "reader",cascade = CascadeType.ALL)
    private List<BookForm> bookForms;

    /**
     * The rating of the reader.
     */
    @Column(name = "reader_rating")
    @Enumerated(EnumType.STRING)
    private ReaderRating rating;

    /**
     * Overrides the default {@code toString()} method to provide a formatted string representation
     * of the reader's information.
     *
     * @return A string representation of the reader.
     */
    @Override
    public String toString() {
        return String.format("Id: %d, First Name: %s, Middle Name: %s, Last Name: %s, Phone Number: %s, Email: %s, Rating: %s",
                id, firstName, middleName, lastName, phoneNumber, email, rating.getDisplayValue());
    }

    public void promote(){
        rating.increase();
        int currentRatingValue=rating.getCurrentValue();
        rating=rating.getNewRating(currentRatingValue);
        rating.setCurrentValue(currentRatingValue);
    }

    public void demote(){
        rating.decrease();
        int currentRatingValue=rating.getCurrentValue();
        rating=rating.getNewRating(currentRatingValue);
        rating.setCurrentValue(currentRatingValue);
    }


    public String getFullName(){
        return firstName+" "+middleName+" "+lastName;
    }
}
