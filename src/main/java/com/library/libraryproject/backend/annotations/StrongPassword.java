package com.library.libraryproject.backend.annotations;

import com.library.libraryproject.backend.annotations.implementations.StrongPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {
    String message() default "Password must contain at least one special character, one uppercase letter,one lowercase letter, one digit, no whitespaces and have a minimum length of 8.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
