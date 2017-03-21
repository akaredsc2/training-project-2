package org.vitaly.textProcessing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.vitaly.textProcessing.util.InputChecker.requireNonEmptyList;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Word implements Token {
    private final List<Symbol> symbolList;

    public Word(List<Symbol> symbolList) {
        requireNonEmptyList(symbolList, "Symbol list");

        for (Symbol symbol : symbolList) {
            String symbolValue = symbol.toString();
            if (symbolValue.matches("\\s+") || symbolValue.matches(PunctuationMark.PUNCTUATION_REGEX)) {
                throw new IllegalArgumentException("Word must not contain whitespaces or punctuation marks!");
            }
        }

        this.symbolList = new ArrayList<>(symbolList);
    }

    public List<Symbol> getSymbolList() {
        return new ArrayList<>(symbolList);
    }

    @Override
    public int getLength() {
        return symbolList.size();
    }

    @Override
    public boolean isWord() {
        return true;
    }

    @Override
    public boolean isPunctuationMark() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word = (Word) o;

        return symbolList.equals(word.symbolList);
    }

    @Override
    public int hashCode() {
        return symbolList.hashCode();
    }

    @Override
    public String toString() {
        return symbolList.stream()
                .map(Symbol::toString)
                .collect(Collectors.joining());
    }
}
