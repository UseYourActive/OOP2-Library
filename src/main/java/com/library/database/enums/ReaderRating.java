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
 * <p>The enum also includes a setter for the value, allowing external modification of the rating value. The {@link #promote()}
 * and {@link #demote()} methods adjust the rating value based on the coefficient and specific rules for rating changes.</p>
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
     * The integer value of the rating.
     */
    @Setter
    @Getter
    private Integer value;

    /**
     * The coefficient used to determine rating changes over time.
     */
    private Integer coefficient = 0;

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

    /**
     * Promotes the rating based on the coefficient and specific rules for rating changes.
     */
    public void promote() {
        coefficient++;

        if (value == -1) {
            setValue(3);
        } else {
            if (coefficient % 5 == 0 && value < 5) {
                value++;
            }
        }
    }

    /**
     * Demotes the rating based on the coefficient and specific rules for rating changes.
     */
    public void demote() {
        coefficient--;

        if (value == -1) {
            setValue(2);
        } else {
            if (coefficient % 5 == 0 && value > 0) {
                value--;
            }
        }
    }

   /**
    * Gets the current Rating based on the current integer value.
    *
    * @return The current Rating.
    * @throws IllegalStateException If no matching Rating is found for the current value.
    */
   private ReaderRating setCurrentRating() throws IllegalStateException {
       for (ReaderRating readerRating : ReaderRating.values()) {
           if (readerRating.getValue().equals(value)) {
               return readerRating;
           }
       }

       throw new IllegalStateException("No matching Rating for value: " + value);
   }
}

