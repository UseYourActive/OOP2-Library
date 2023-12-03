package com.project.backend.annotations.implementations;

import com.project.backend.annotations.GenreValidation;
import com.project.database.entities.Genre;
import com.project.database.repositories.GenreRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenreValidator implements ConstraintValidator<GenreValidation, String> {
    @Override
    public void initialize(GenreValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String genre, ConstraintValidatorContext context) {

        GenreRepository repository = new GenreRepository();

        for (Genre validGenre : repository.getAll().toList()) {
            if (validGenre.toString().equals(genre)) {
                return true;
            }
        }

        return false;
    }
}
