package com.project.backend.annotations;


import com.project.backend.annotations.implementations.GenreValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenreValidator.class)
public @interface GenreValidation {
    String message() default "Invalid genre. Please choose a valid one.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}