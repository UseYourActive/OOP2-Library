package com.library.annotations;

import com.library.entities.Genre;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenreValidator implements ConstraintValidator<ValidGenre, String> {
    @Override
    public void initialize(ValidGenre constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String genre, ConstraintValidatorContext context) {
        for (Genre validGenre : Genre.values()) {
            if (validGenre.getValue().equals(genre)) {
                return true;
            }
        }

        return false;
    }
}
