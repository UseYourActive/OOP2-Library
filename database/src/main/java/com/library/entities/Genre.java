package com.library.entities;

import lombok.Getter;

//@Getter
public enum Genre {
    ACTION_AND_ADVENTURE("Action and adventure"),
    CLASSICS("Classics"),
    DETECTIVE_AND_MYSTERY("Detective and mystery"),
    FANTASY("Fantasy"),
    HISTORY_FICTION("History fiction"),
    HORROR("Horror"),
    LITERARY_FICTION("Literary fiction"),
    ROMANCE("Romance"),
    SCIENCE_FICTION("Science fiction"),
    SUSPENSE_AND_THRILLERS("Suspense and thrillers");

    private final String value;

    Genre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
