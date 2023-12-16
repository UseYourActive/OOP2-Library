package com.library.frontend.utils;

import com.library.backend.annotations.*;
import com.library.database.entities.Genre;
import com.library.database.repositories.GenreRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateBigDecimalString(String value) {
        return new BigDecimalStringValidator().isValid(value, null);
    }

    public static boolean validateDate(String date) {
        return new DateValidator().isValid(date, null);
    }

    public static boolean validateGenre(String genre) {
        return new GenreValidator().isValid(genre, null);
    }

    public static boolean validateISBN(String isbn) {
        return new ISBNValidator().isValid(isbn, null);
    }

    public static boolean validateStrongPassword(String password) {
        return new StrongPasswordValidator().isValid(password, null);
    }

    private static final class BigDecimalStringValidator implements ConstraintValidator<ValidBigDecimalStringValidation, String> {
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

    private static final class DateValidator implements ConstraintValidator<DateValidation, String> {
        private static final String DATE_PATTERN = "yyyy-MM-dd";

        @Override
        public void initialize(DateValidation constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(String date, ConstraintValidatorContext context) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
            simpleDateFormat.setLenient(false);

            try {
                simpleDateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    private static final class GenreValidator implements ConstraintValidator<GenreValidation, String> {
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

    private static final class ISBNValidator implements ConstraintValidator<ISBNValidation, String> {
        @Override
        public void initialize(ISBNValidation constraintAnnotation) {
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

    private static final class StrongPasswordValidator implements ConstraintValidator<StrongPasswordValidation, String> {
        private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        @Override
        public void initialize(StrongPasswordValidation constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(String password, ConstraintValidatorContext context) {
            return password != null && Pattern.matches(PASSWORD_PATTERN, password);
        }
    }
}
