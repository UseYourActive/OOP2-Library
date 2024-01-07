package com.library.database.entities;

import com.library.database.enums.Ratings;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reader_ratings")
public class ReaderRating implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", nullable = false)
    private Ratings rating;

    @Column(name = "coefficient", nullable = false)
    private int coefficient;

    @Column(name = "current_value", nullable = false)
    private double currentValue;

   //@OneToMany(mappedBy = "reader_rating")
   //private List<Reader> readers;

    @Override
    public Long getId() {
        return id;
    }

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

    public void decrease() {
        coefficient--;

        if (currentValue == -1) {
            currentValue=2;
        } else {
            if ( Math.abs(coefficient) % 5 == 0 && currentValue > 0) {
                currentValue--;
            }
        }
    }

    public void updateRating() throws IllegalStateException {
        for (Ratings rating : Ratings.values()) {
            if (rating.getValue()==currentValue) {
                this.rating = rating;
            }
        }
    }

    @Override
    public String toString() {
        return rating.getDisplayValue();
    }
}
