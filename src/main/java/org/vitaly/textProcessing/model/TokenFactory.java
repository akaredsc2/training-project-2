package org.vitaly.textProcessing.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vitaly on 2017-03-19.
 */
public class TokenFactory {
    private String notWhitespaceRegex;

    public TokenFactory() {
        this.notWhitespaceRegex = "[^-\\s]";
    }

    public Token createWord(String string) {
        String[] symbolStrings = string.split(notWhitespaceRegex);

        List<Symbol> symbolList = Arrays.stream(symbolStrings)
                .map(Symbol::new)
                .collect(Collectors.toList());

        return new Word(symbolList);
    }

    public Token createPunctuation(String string) {
        Symbol symbol = new Symbol(string);
        return new PunctuationMark(symbol);
    }
}
