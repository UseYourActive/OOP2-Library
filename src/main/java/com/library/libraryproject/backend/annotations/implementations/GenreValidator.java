package com.library.libraryproject.backend.annotations.implementations;

import com.library.libraryproject.backend.annotations.Genre;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenreValidator implements ConstraintValidator<Genre, String> {
    @Override
    public void initialize(Genre constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String genre, ConstraintValidatorContext context) {
        for (com.library.libraryproject.database.enums.Genre validGenre : com.library.libraryproject.database.enums.Genre.values()) {
            if (validGenre.getValue().equals(genre)) {
                return true;
            }
        }

        return false;
    }
}
