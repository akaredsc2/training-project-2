package org.vitaly.textProcessing.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by vitaly on 2017-03-19.
 */
public class PunctuationMarkTest {
    private PunctuationMark punctuationMark;
    private Symbol punctuationSymbol;
    private String punctuationMarkValue;

    @Before
    public void setUp() throws Exception {
        punctuationMarkValue = "!";
        punctuationSymbol = new Symbol(punctuationMarkValue);
        punctuationMark = new PunctuationMark(punctuationSymbol);
    }

    @Test
    public void punctuationMarkIsNotAWord() throws Exception {
        assertFalse(punctuationMark.isWord());
    }

    @Test
    public void punctuationMarkIsPunctuationMark() throws Exception {
        assertTrue(punctuationMark.isPunctuationMark());
    }

    @Test
    public void punctuationMarkLengthIsEqualToOne() throws Exception {
        assertThat(punctuationMark.getLength(), equalTo(1));
    }

    @Test
    public void punctuationMarkToStringIsEqualToPunctuationSymbolValue() throws Exception {
        assertThat(punctuationMark.toString(), equalTo(punctuationSymbol.getValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingPunctuationMarkWithNullSymbolShouldThrowException() throws Exception {
        new PunctuationMark(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingPunctuationMarkWithNotPunctuationSymbolShouldThrowException() throws Exception {
        String nonPunctuationValue = "a";
        Symbol nonPunctuationSymbol = new Symbol(nonPunctuationValue);
        new PunctuationMark(nonPunctuationSymbol);
    }

    @Test
    public void punctuationMarkToStringReturnsSymbolValue() throws Exception {
        assertThat(punctuationMark.toString(), equalTo(punctuationSymbol.getValue()));
    }
}