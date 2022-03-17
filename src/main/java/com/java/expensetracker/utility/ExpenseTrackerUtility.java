package com.java.expensetracker.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ExpenseTrackerUtility {
    public static final int ID_LENGTH = 5;
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"\'\\\\]");

    private ExpenseTrackerUtility() {
    }

    public static boolean isValidString(final String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        }

        return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
    }

    public static String generateId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
}
