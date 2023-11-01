package com.library.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenreValidator.class)
public @interface ValidGenre {
    String message() default "Invalid genre. Please choose a valid one.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
