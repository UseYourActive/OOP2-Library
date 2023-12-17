package com.library.database.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Rating {
    NONE("None", 0),
    ZERO_STAR("Zero star", 1),
    ONE_STAR("One star", 2),
    TWO_STAR("Two star", 3),
    THREE_STAR("Three star", 4),
    FOUR_STAR("Four star", 5),
    FIVE_STAR("Five star", 6);

    Rating(String displayValue, Integer value) {
        this.displayValue = displayValue;
        this.value = value;
    }

    private final String displayValue;
    @Setter private Integer value;
    private Integer coefficient = 0;

    public void promote() {
        coefficient++;

        if(value == 0){
            setValue(4);
        }
        else {
            if (coefficient % 5 == 0 && value < 6) {
                value++;
            }
        }
    }

    public void demote() {
        coefficient--;

        if(value == 0){
            setValue(3);
        }
        else {
            if (coefficient % 5 == 0 && value > 1) {
                value--;
            }
        }
    }

    public Rating getCurrentRating() throws IllegalStateException {
        for (Rating rating : Rating.values()){
            if(rating.getValue().equals(value)){
                return rating;
            }
        }

        throw new IllegalStateException("No matching Rating for value: " + value);
    }
}
