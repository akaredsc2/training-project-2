package org.vitaly.textProcessing.parser;

import java.util.List;

/**
 * Created by vitaly on 2017-03-19.
 */
public class AppendToCurrentTokenStrategy implements SavingStrategy {
    @Override
    public void save(String currentToken, String splitter, List<String> result) {
        String appendedCurrentToken = currentToken + splitter;
        saveTrimmed(appendedCurrentToken, result);
    }
}
