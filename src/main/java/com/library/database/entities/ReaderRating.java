package com.library.database.entities;

import com.library.database.enums.Ratings;
import jakarta.persistence.*;
import lombok.*;

/**
 * The {@code ReaderRating} class represents the rating of a library reader, including a coefficient
 * that influences the rating and the current calculated value.
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
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.Builder
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reader_ratings")
public class ReaderRating implements DBEntity {

    /**
     * The unique identifier for the reader rating.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;

    /**
     * The overall rating value represented by an enumeration.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "rating", nullable = false)
    private Ratings rating;

    /**
     * The coefficient influencing the rating.
     */
    @Column(name = "coefficient", nullable = false)
    private int coefficient;

    /**
     * The current calculated value of the reader's rating.
     */
    @Column(name = "current_value", nullable = false)
    private double currentValue;

    /**
     * Retrieves the unique identifier for the reader rating.
     *
     * @return The unique identifier for the reader rating.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Increases the coefficient and adjusts the current value based on certain conditions.
     */
    public void increase() {
        coefficient++;

        if (currentValue == -1) {
            currentValue = 3;
        } else {
            if (coefficient % 5 == 0 && currentValue < 5) {
                currentValue++;
            }
        }
    }

    /**
     * Decreases the coefficient and adjusts the current value based on certain conditions.
     */
    public void decrease() {
        coefficient--;

        if (currentValue == -1) {
            currentValue = 2;
        } else {
            if (Math.abs(coefficient) % 5 == 0 && currentValue > 0) {
                currentValue--;
            }
        }
    }

    /**
     * Updates the overall rating based on the current calculated value.
     *
     * @throws IllegalStateException if the current value does not match any valid rating.
     */
    public void updateRating() throws IllegalStateException {
        for (Ratings rating : Ratings.values()) {
            if (rating.getValue() == currentValue) {
                this.rating = rating;
            }
        }
    }

    /**
     * Overrides the default {@code toString()} method to provide a string representation of the reader's rating.
     *
     * @return A string representation of the reader's rating.
     */
    @Override
    public String toString() {
        return rating.getDisplayValue();
    }
}
