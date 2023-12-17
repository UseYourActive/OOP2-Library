package com.library.frontend.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateBigDecimalString(String value) {
        return new BigDecimalStringValidator().isValid(value);
    }

    public static boolean validateDate(String date) {
        return new DateValidator().isValid(date);
    }

//    public static boolean validateGenre(String genre) {
//        return new GenreValidator().isValid(genre, null);
//    }

    public static boolean validateISBN(String isbn) {
        return new ISBNValidator().isValid(isbn);
    }

    public static boolean validateStrongPassword(String password) {
        return new StrongPasswordValidator().isValid(password);
    }

    private static final class BigDecimalStringValidator {
        public boolean isValid(String value) {
            try {
                new BigDecimal(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    private static final class DateValidator {
        private static final String DATE_PATTERN = "yyyy-MM-dd";
        public boolean isValid(String date) {
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

//    private static final class GenreValidator {
//        public boolean isValid(String genre, ConstraintValidatorContext context) {
//            GenreRepository repository = GenreRepository.getInstance();
//
//            for (Genre validGenre : repository.findAll()) {
//                if (validGenre.toString().equals(genre)) {
//                    return true;
//                }
//            }
//
//            return false;
//        }
//    }

    private static final class ISBNValidator {
        public boolean isValid(String isbn) {
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

    private static final class StrongPasswordValidator {
        private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

        public boolean isValid(String password) {
            return password != null && Pattern.matches(PASSWORD_PATTERN, password);
        }
    }
}
