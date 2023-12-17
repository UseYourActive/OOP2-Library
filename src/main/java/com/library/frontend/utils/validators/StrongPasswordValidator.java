package com.library.frontend.utils.validators;

import java.util.regex.Pattern;

public final class StrongPasswordValidator implements Validator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

    public boolean isValid(String password) {
        return password != null && Pattern.matches(PASSWORD_PATTERN, password);
    }
}
