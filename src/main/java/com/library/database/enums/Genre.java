package com.library.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
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


    @Override
    public String toString() {
        return value;
    }

    public static Genre getValueOf(String value){
        for(Genre genre:Genre.values()){
            if(Objects.equals(genre.value, value)){
                return genre;
            }
        }
        return null;
    }
}
