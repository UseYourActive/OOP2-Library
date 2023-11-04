package com.library.libraryproject.backend.annotations.implementations;

import com.library.libraryproject.backend.annotations.ISBN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public void initialize(ISBN constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        isbn = isbn.replaceAll("[\\s-]", "");

        return isValidISBN10(isbn) || isValidISBN13(isbn);
    }

    private boolean isValidISBN10(String isbn) {
        if (isbn.length() != 10) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (10 - i) * digit;
        }

        char lastChar = isbn.charAt(9);
        if (lastChar == 'X' || lastChar == 'x') {
            sum += 10;
        } else {
            sum += Character.getNumericValue(lastChar);
        }

        return sum % 11 == 0;
    }

    private boolean isValidISBN13(String isbn) {
        if (isbn.length() != 13) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : 3 * digit;
        }

        int checkDigit = Character.getNumericValue(isbn.charAt(12));
        return (10 - (sum % 10)) % 10 == checkDigit;
    }
}
