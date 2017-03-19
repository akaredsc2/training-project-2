package org.vitaly.textProcessing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class WordTest {
    private List<String> stringValues;
    private List<Symbol> symbols;
    private Word word;

    @Before
    public void setUp() throws Exception {
        stringValues = new ArrayList<>();
        Collections.addAll(stringValues, "a", "b", "c", "d", "e", "f");
        symbols = stringValues.stream()
                .map(Symbol::new)
                .collect(Collectors.toList());
        word = new Word(symbols);
    }

    @Test
    public void getSymbolListReturnsNewInstanceEachCall() throws Exception {
        List<Symbol> firstCallList = word.getSymbolList();
        List<Symbol> secondCallList = word.getSymbolList();

        assertThat(firstCallList, allOf(
                equalTo(secondCallList),
                not(sameInstance(secondCallList))));
    }

    @Test
    public void modifyingSymbolListDoesNotAffectWordSymbolList() throws Exception {
        List<Symbol> symbolList = word.getSymbolList();

        symbolList.clear();

        assertThat(symbolList, not(equalTo(word.getSymbolList())));
    }

    @Test
    public void wordLengthIsEqualToSuppliedAtConstructionSymbolListLength() throws Exception {
        assertEquals(word.getLength(), symbols.size());
    }

    @Test
    public void wordIsAlwaysWord() throws Exception {
        assertTrue(word.isWord());
    }

    @Test
    public void wordIsNotPunctuationMark() throws Exception {
        assertFalse(word.isPunctuationMark());
    }

    @Test
    public void wordsWithEqualWordListsAreEqual() throws Exception {
        List<Symbol> symbolListCopy = new ArrayList<>(symbols);
        Word otherWord = new Word(symbolListCopy);

        assertEquals(otherWord, word);
    }

    @Test
    public void wordsWithEqualWordListsHaveEqualHashCodes() throws Exception {
        List<Symbol> symbolListCopy = new ArrayList<>(symbols);
        Word otherWord = new Word(symbolListCopy);

        assertEquals(otherWord.hashCode(), word.hashCode());
    }

    @Test
    public void wordToStringReturnsConcatenatedSymbolValues() throws Exception {
        String actualString = word.toString();

        String expectedString = symbols.stream()
                .map(Symbol::getValue)
                .collect(Collectors.joining());

        assertThat(actualString, equalTo(expectedString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructingWordWithNullSymbolListShouldThrowException() throws Exception {
        new Word(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructingWordWithEmptySymbolListShouldThrowException() throws Exception {
        new Word(Collections.emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructingWordWithSymbolListContainingWhitespaceShouldThrowException() throws Exception {
        String whitespaceValue = " ";
        Symbol whitespaceSymbol = new Symbol(whitespaceValue);
        symbols.add(whitespaceSymbol);
        new Word(symbols);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructingWordWithSymbolListContainingPunctuationMarkShouldThrowException() throws Exception {
        String punctuationValue = "?";
        Symbol punctuationSymbol = new Symbol(punctuationValue);
        symbols.add(punctuationSymbol);
        new Word(symbols);
    }
}