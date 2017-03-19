package org.vitaly.textProcessing.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.vitaly.textProcessing.model.PunctuationMark.PUNCTUATION_REGEX;
import static org.vitaly.textProcessing.util.InputChecker.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class TokenFactory {
    public Token createWord(String string) {
        requireNotContainWhitespaces(string);
        requireNotContainPunctuation(string);

        String[] symbolStrings = string.split("");

        List<Symbol> symbolList = Arrays.stream(symbolStrings)
                .map(Symbol::new)
                .collect(Collectors.toList());

        return new Word(symbolList);
    }

    public Token createPunctuation(String string) {
        requirePunctuation(string);

        Symbol symbol = new Symbol(string);
        return new PunctuationMark(symbol);
    }

    public Token createToken(String string) {
        requireNonEmptyString(string, STRING);

        return string.matches(PUNCTUATION_REGEX) ? createPunctuation(string) : createWord(string);
    }
}
