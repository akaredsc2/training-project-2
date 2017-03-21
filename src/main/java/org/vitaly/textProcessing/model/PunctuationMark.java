package org.vitaly.textProcessing.model;

import static org.vitaly.textProcessing.util.InputChecker.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class PunctuationMark implements Token {
    public static final String END_OF_SENTENCE_REGEX = "[.!?]";
    public static final String NOT_END_OF_SENTENCE_PUNCTUATION_REGEX = "[:;,\\-\\(\\)\\[\\]`]";
    public static final String PUNCTUATION_REGEX = END_OF_SENTENCE_REGEX + "|" + NOT_END_OF_SENTENCE_PUNCTUATION_REGEX;

    private final Symbol symbol;

    public PunctuationMark(Symbol symbol) {
        requireNonNull(symbol, SYMBOL);
        requirePunctuation(symbol.toString());

        this.symbol = symbol;
    }

    @Override
    public boolean isWord() {
        return false;
    }

    @Override
    public boolean isPunctuationMark() {
        return true;
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
