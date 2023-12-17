package com.library.frontend.utils.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateValidator implements Validator {
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
