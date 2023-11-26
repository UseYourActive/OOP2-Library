package com.library.backend.annotations.implementations;


import com.library.backend.annotations.PhoneNumberValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {

    private static final String PHONE_NUMBER_PATTERN = "^[0-9]{10}$";

    @Override
    public void initialize(PhoneNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return Pattern.matches(PHONE_NUMBER_PATTERN, phoneNumber);
    }
}
