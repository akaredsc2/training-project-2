package org.vitaly.textProcessing.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by vitaly on 20.03.17.
 */
public class TokenFactoryTest {
    private TokenFactory factory;
    private String wordString;
    private String punctuationString;
    private Token token;

    @Before
    public void setUp() throws Exception {
        factory = new TokenFactory();
        wordString = "word";
        punctuationString = ":";
    }

    @Test
    public void createWordReturnsWord() throws Exception {
        token = factory.createWord(wordString);

        assertThat(token, instanceOf(Word.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordWithNullStringShouldThrowException() throws Exception {
        factory.createWord(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordWithEmptyStringShouldThrowException() throws Exception {
        factory.createWord("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordWithStringContainingWhitespaceShouldThrowException() throws Exception {
        factory.createWord("aaa bbb");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWordWithStringContainingPunctuationShouldThrowException() throws Exception {
        factory.createWord("aaa,bbb");
    }

    @Test
    public void createPunctuationReturnsPunctuation() throws Exception {
        token = factory.createPunctuation(punctuationString);

        assertThat(token, instanceOf(PunctuationMark.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPunctuationWithNullStringShouldThrowException() throws Exception {
        factory.createPunctuation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPunctuationWithEmptyStringShouldThrowException() throws Exception {
        factory.createPunctuation("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPunctuationWithNonPunctuationStringShouldThrowException() throws Exception {
        factory.createPunctuation("*");
    }

    @Test
    public void supplyingWordStringToCreateTokenReturnsWord() throws Exception {
        token = factory.createToken(wordString);

        assertThat(token, instanceOf(Word.class));
    }

    @Test
    public void supplyingPunctuationStringToCreateTokenReturnsPunctuation() throws Exception {
        token = factory.createToken(punctuationString);

        assertThat(token, instanceOf(PunctuationMark.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTokenWithNullStringShouldThrowException() throws Exception {
        factory.createToken(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTokenWithEmptyStringShouldThrowException() throws Exception {
        factory.createToken("");
    }
}