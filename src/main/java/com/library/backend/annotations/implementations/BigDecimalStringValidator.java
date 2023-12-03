package com.library.backend.annotations.implementations;

import com.library.backend.annotations.ValidBigDecimalStringValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class BigDecimalStringValidator implements ConstraintValidator<ValidBigDecimalStringValidation, String> {
    @Override
    public void initialize(ValidBigDecimalStringValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
