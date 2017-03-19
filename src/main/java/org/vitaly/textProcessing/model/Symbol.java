package org.vitaly.textProcessing.model;

import static org.vitaly.textProcessing.util.InputChecker.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Symbol {
    private final String value;

    public Symbol(String value) {
        requireNonEmptyString(value, SYMBOL);
        requireStringWithOfLengthOne(value, SYMBOL);

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
