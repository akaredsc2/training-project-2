package org.vitaly.textProcessing.parser;

import java.util.List;

import static org.vitaly.textProcessing.util.InputChecker.requireNonEmptyList;
import static org.vitaly.textProcessing.util.InputChecker.requireNonNull;

/**
 * Created by vitaly on 2017-03-19.
 */
public class AppendToCurrentTokenStrategy implements SavingStrategy {
    @Override
    public void save(String currentToken, String splitter, List<String> result) {
        requireNonNull(currentToken, "Current token string");
        requireNonNull(splitter, "Current splitter string");
        requireNonEmptyList(result, "Result list");

        String appendedCurrentToken = currentToken + splitter;
        saveTrimmed(appendedCurrentToken, result);
    }
}
