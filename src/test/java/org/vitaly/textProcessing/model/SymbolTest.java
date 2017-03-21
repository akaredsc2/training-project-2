package org.vitaly.textProcessing.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by vitaly on 2017-03-19.
 */
public class SymbolTest {
    @Test
    public void newSymbolContainsSuppliedString() throws Exception {
        String symbolValue = " ";
        Symbol symbol = new Symbol(symbolValue);

        assertThat(symbol.toString(), equalTo(symbolValue));
    }

    @Test(expected = IllegalArgumentException.class)
    public void newSymbolOfNullShouldThrowException() throws Exception {
        new Symbol(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newSymbolOfEmptyStringShouldThrowException() throws Exception {
        new Symbol("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void newSymbolOfStringWithLengthGreaterThanOneShouldThrowException() throws Exception {
        new Symbol("...");
    }
}