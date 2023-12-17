package com.library.frontend.utils.validators;

import java.math.BigDecimal;

public final class BigDecimalStringValidator implements Validator {
    public boolean isValid(String value) {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
