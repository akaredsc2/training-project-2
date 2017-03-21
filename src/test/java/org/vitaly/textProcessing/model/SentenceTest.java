package org.vitaly.textProcessing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

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

        tokenList = Stream.of("a", "bb", ",", "ccc", "-", "dd", "e", "!")
                .map(factory::createToken)
                .collect(Collectors.toList());

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
    public void replaceTokensReturnsNewSentenceInstanceWithSameSize() throws Exception {
        List<Token> initialSentenceTokens = sentence.getTokenList();
        int initialSentenceSize = initialSentenceTokens.size();

        Sentence newSentence = sentence.replaceTokens(token -> false, factory.createToken("null"));

        List<Token> updatedTokens = newSentence.getTokenList();

        assertThat(updatedTokens, allOf(
                equalTo(initialSentenceTokens),
                not(sameInstance(initialSentenceTokens)),
                hasSize(initialSentenceSize)));
    }

    @Test
    public void sentenceDoesNotContainMatchingTokensAfterReplace() throws Exception {
        List<Token> matchingTokens = sentence.getTokenList().stream()
                .filter(tokenPredicate)
                .collect(Collectors.toList());

        Sentence newSentence = sentence.replaceTokens(tokenPredicate, substituteToken);

        List<Token> updatedTokens = newSentence.getTokenList();

        assertThat(updatedTokens, not(contains(matchingTokens.toArray())));
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