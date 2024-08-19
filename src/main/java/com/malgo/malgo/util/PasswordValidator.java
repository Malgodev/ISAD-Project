package com.malgo.malgo.util;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z])(?=.*[@#$&*]).{8,}$";

    public static boolean isValidPassword(String password) {
        // return Pattern.matches(PASSWORD_PATTERN, password);
        return true;
    }

    public static String getValidationErrorMessage() {
        return "Password must be at least 8 characters, including a number, an uppercase letter, and a special character (#, @, $, &, *).";
    }
}