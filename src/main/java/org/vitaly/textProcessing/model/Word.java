package org.vitaly.textProcessing.model;

import java.util.List;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Word implements Token {
    private final List<Symbol> symbolList;

    public Word(List<Symbol> symbolList) {
        this.symbolList = symbolList;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return symbolList != null ? symbolList.equals(word.symbolList) : word.symbolList == null;
    }

    @Override
    public int hashCode() {
        return symbolList != null ? symbolList.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Symbol symbol : symbolList) {
            builder.append(symbol.getValue());
        }
        return builder.toString();
    }
}
