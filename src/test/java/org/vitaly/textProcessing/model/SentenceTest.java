package org.vitaly.textProcessing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Created by vitaly on 20.03.17.
 */
public class SentenceTest {
    private List<Token> tokenList;
    private Sentence sentence;
    private TokenFactory factory;
    private Token substituteToken;
    private Predicate<Token> tokenPredicate;

    @Before
    public void setUp() throws Exception {
        factory = new TokenFactory();
        Token token1 = factory.createToken("a");
        Token token2 = factory.createToken("bb");
        Token token3 = factory.createToken(",");
        Token token4 = factory.createToken("ccc");
        Token token5 = factory.createToken("-");
        Token token6 = factory.createToken("dd");
        Token token7 = factory.createToken("e");
        Token token8 = factory.createToken("!");
        tokenList = new ArrayList<>();
        Collections.addAll(tokenList, token1, token2, token3, token4, token5, token6, token7, token8);
        sentence = new Sentence(tokenList);
        substituteToken = factory.createToken("null");
        tokenPredicate = token -> token.isWord() && token.getLength() == 2;
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingSentenceWithNullTokenListShouldThrowException() throws Exception {
        new Sentence(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingSentenceWithEmptyTokenListShouldThrowException() throws Exception {
        new Sentence(new ArrayList<>());
    }

    @Test
    public void replaceTokensReturnsNewSentenceInstance() throws Exception {
        List<Token> initialSentenceTokens = sentence.getTokenList();

        Sentence newSentence = sentence.replaceTokens(token -> false, factory.createToken("null"));

        List<Token> updatedTokens = newSentence.getTokenList();

        assertThat(updatedTokens, allOf(
                equalTo(initialSentenceTokens),
                not(sameInstance(initialSentenceTokens))));
    }

    @Test
    public void replaceTokensDoesNotChangeSentenceTokenListSize() throws Exception {
        List<Token> initialSentenceTokens = sentence.getTokenList();

        Sentence newSentence = sentence.replaceTokens(token -> true, substituteToken);

        List<Token> updatedTokens = newSentence.getTokenList();

        assertThat(initialSentenceTokens.size(), equalTo(updatedTokens.size()));
    }

    @Test
    public void replacingReplacesAllMatchingTokens() throws Exception {
        List<Token> matchingTokens = sentence.getTokenList().stream()
                .filter(tokenPredicate)
                .collect(Collectors.toList());

        Sentence newSentence = sentence.replaceTokens(tokenPredicate, substituteToken);

        List<Token> updatedTokens = newSentence.getTokenList();

        assertThat(updatedTokens, not(contains(matchingTokens.toArray())));
    }

    @Test
    public void replaceTokensWithMatchingPredicateDoesNotChangeOriginalSentence() throws Exception {
        List<Token> initialSentenceTokens = sentence.getTokenList();

        Sentence newSentence = sentence.replaceTokens(tokenPredicate, substituteToken);

        List<Token> updatedTokens = newSentence.getTokenList();

        assertThat(updatedTokens, not(equalTo(initialSentenceTokens)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void replacingTokenWithNullPredicateShouldThrowException() throws Exception {
        sentence.replaceTokens(null, tokenList.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void replacingTokenWithNullTokenShouldThrowException() throws Exception {
        sentence.replaceTokens(Objects::nonNull, null);
    }

    @Test
    public void getTokenListReturnsNewInstance() throws Exception {
        List<Token> firstCallList = sentence.getTokenList();
        List<Token> secondCallList = sentence.getTokenList();

        assertThat(firstCallList, allOf(
                equalTo(secondCallList),
                not(sameInstance(secondCallList))));
    }

    @Test
    public void toStringReturnsConcatenatedTokensDelimitedWithWhitespaces() throws Exception {
        String sentenceString = sentence.toString();

        String expectedString = tokenList.stream()
                .map(Token::toString)
                .collect(Collectors.joining(" "));

        assertThat(sentenceString, equalTo(expectedString));
    }
}