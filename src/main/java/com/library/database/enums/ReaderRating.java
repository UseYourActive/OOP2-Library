package com.library.database.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Rating} enum represents different rating levels that can be assigned to items in a library system.
 * Each rating has a corresponding display value, an integer value, and a coefficient that determines how the rating
 * changes over time. The enum provides methods for promoting, demoting, and obtaining the current rating based on the
 * integer value.
 *
 * <p>The ratings range from "None" to "Five star," with corresponding values from 0 to 6. Ratings can be promoted or
 * demoted based on a coefficient, and specific rules are applied to adjust the rating values. The enum includes methods
 * to retrieve the current rating based on the current integer value.</p>
 *
 * <p>The enum also includes a setter for the value, allowing external modification of the rating value. The {@link #increase()}
 * and {@link #decrease()} methods adjust the rating value based on the coefficient and specific rules for rating changes.</p>
 *
 * @see lombok.Getter
 * @see lombok.Setter
 */

public enum ReaderRating {
    /**
     * No rating.
     */
    NONE("None", -1),

    /**
     * Zero-star rating.
     */
    ZERO_STAR("Zero star", 0),

    /**
     * One-star rating.
     */
    ONE_STAR("One star", 1),

    /**
     * Two-star rating.
     */
    TWO_STAR("Two star", 2),

    /**
     * Three-star rating.
     */
    THREE_STAR("Three star", 3),

    /**
     * Four-star rating.
     */
    FOUR_STAR("Four star", 4),

    /**
     * Five-star rating.
     */
    FIVE_STAR("Five star", 5);

    /**
     * The display value of the rating.
     */
    @Getter
    private final String displayValue;

    /**
     * The default value of the rating.
     */
    @Getter
    private final int value;

    /**
     * The current value used to determine current rating.
     */
    @Setter
    @Getter
    private int currentValue;

    /**
     * The coefficient used to determine rating changes over time.
     */
    private double coefficient = 0;

    /**
     * Constructs a new Rating with the specified display value and integer value.
     *
     * @param displayValue The display value of the rating.
     * @param value        The integer value of the rating.
     */
    ReaderRating(String displayValue, Integer value) {
        this.displayValue = displayValue;
        this.value = value;
    }


    public void increase() {
        coefficient++;

        if (value == -1) {
            currentValue=3;
        } else {
            if (coefficient % 5 == 0 && currentValue < 5) {
                currentValue++;
            }
        }
    }


    public void decrease() {
        coefficient--;

        if (value == -1) {
            currentValue=2;
        } else {
            if ( Math.abs(coefficient) % 5 == 0 && currentValue > 0) {
                currentValue--;
            }
        }
    }


   public ReaderRating getNewRating(int currentValue) throws IllegalStateException {
       for (ReaderRating readerRating : ReaderRating.values()) {
           if (readerRating.value==currentValue) {
               return readerRating;
           }
       }

       throw new IllegalStateException("No matching Rating for value: " + value);
   }
}

