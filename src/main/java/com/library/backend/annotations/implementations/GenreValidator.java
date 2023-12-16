package com.library.backend.annotations.implementations;

import com.library.backend.annotations.GenreValidation;
import com.library.database.entities.Genre;
import com.library.database.repositories.GenreRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenreValidator implements ConstraintValidator<GenreValidation, String> {
    @Override
    public void initialize(GenreValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String genre, ConstraintValidatorContext context) {

        GenreRepository repository = GenreRepository.getInstance();

        for (Genre validGenre : repository.findAll()) {
            if (validGenre.toString().equals(genre)) {
                return true;
            }
        }

        return false;
    }
}
