package org.vitaly.textProcessing.parser;

import java.util.List;

/**
 * Created by vitaly on 2017-03-19.
 */
public interface SavingStrategy {
    void save(String currentToken, String splitter, List<String> result);

    default void saveTrimmed(String string, List<String> result) {
        String trimmedString = string.trim();
        if (!trimmedString.isEmpty()) {
            result.add(trimmedString);
        }
    }
}
