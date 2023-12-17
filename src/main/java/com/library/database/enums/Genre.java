package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Genre {
    FICTION("Fiction"),
    NON_FICTION("Non-fiction"),
    MYSTERY("Mystery"),
    SCIENCE_FICTION("Science fiction"),
    ROMANCE("Romance"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    HISTORY("History"),
    BIOGRAPHY("Biography"),
    SELF_HELP("Self help"),
    BUSINESS("Business"),
    CHILDREN("Children"),
    POETRY("Poetry"),
    DRAMA("Drama"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    ADVENTURE("Adventure"),
    PHILOSOPHY("Philosophy"),
    TRAVEL("Travel");

    private final String value;
}
