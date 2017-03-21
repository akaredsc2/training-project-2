package org.vitaly.textProcessing.util;

import org.vitaly.textProcessing.model.PunctuationMark;

import java.util.List;

/**
 * Created by vitaly on 2017-03-19.
 */
public class InputChecker {
    public static final String SYMBOL = "Symbol";
    public static final String STRING = "String";

    private InputChecker() {
    }

    public static void requireNonNull(Object object, String objectDescription) {
        if (object == null) {
            throw new IllegalArgumentException(objectDescription + " must not be null!");
        }
    }

    public static void requireNonEmptyString(String string, String stringDescription) {
        requireNonNull(string, stringDescription);
        if (string.isEmpty()) {
            throw new IllegalArgumentException(stringDescription + " must not be empty string!");
        }
    }

    public static void requireStringWithOfLengthOne(String string, String stringDescription) {
        requireNonEmptyString(string, stringDescription);
        if (string.length() != 1) {
            throw new IllegalArgumentException(stringDescription + " must be string with length of 1!");
        }
    }

    public static void requireNonEmptyList(List list, String listDescription) {
        requireNonNull(list, listDescription);
        if (list.isEmpty()) {
            throw new IllegalArgumentException(listDescription + " must not be empty list!");
        }
    }

    public static void requireInRange(int number, int from, int to, String numberDescription) {
        if (number < from || number >= to) {
            throw new IndexOutOfBoundsException(
                    numberDescription + "must be between " + from + " inclusive and " + to + " exclusive!");
        }
    }

    public static void requirePunctuation(String string) {
        requireNonEmptyString(string, STRING);
        if (!string.matches(PunctuationMark.PUNCTUATION_REGEX)) {
            throw new IllegalArgumentException("Supplied string must be punctuation mark!");
        }
    }

    public static void requireNotContainPunctuation(String string) {
        requireNonEmptyString(string, STRING);
        if (string.split(PunctuationMark.PUNCTUATION_REGEX).length > 1) {
            throw new IllegalArgumentException("String must not contain punctuation!");
        }
    }

    public static void requireNotContainWhitespaces(String string) {
        requireNonEmptyString(string, STRING);
        if (string.split("\\s+").length > 1) {
            throw new IllegalArgumentException("String must not contain whitespaces!");
        }
    }
}
