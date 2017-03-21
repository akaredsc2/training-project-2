package org.vitaly.textProcessing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by vitaly on 2017-03-21.
 */
public class TextTest {
    private List<Sentence> sentenceList;
    private Text text;
    private TokenFactory factory;
    private int sentenceNumber;

    @Before
    public void setUp() throws Exception {
        factory = new TokenFactory();
        Sentence firstSentence = new Sentence(Stream.of("aaa", "!")
                .map(factory::createToken)
                .collect(Collectors.toList()));
        Sentence secondSentence = new Sentence(Stream.of("bbb", "?")
                .map(factory::createToken)
                .collect(Collectors.toList()));

        sentenceList = Arrays.asList(firstSentence, secondSentence);
        text = new Text(sentenceList);
        sentenceNumber = 0;
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingTextWithNullSentenceListShouldThrowException() throws Exception {
        new Text(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingTextWithEmptySentenceListShouldThrowException() throws Exception {
        new Text(new ArrayList<>());
    }

    @Test
    public void getSentenceCountReturnsAmountOfSentencesSuppliedOnCreation() throws Exception {
        int sentenceCount = text.getSentenceCount();

        int sentenceListSize = sentenceList.size();

        assertThat(sentenceCount, equalTo(sentenceListSize));
    }

    @Test
    public void getSentenceByNumberReturnsSentenceFromSentenceList() throws Exception {
        Sentence sentenceFromList = text.getSentenceList().get(sentenceNumber);

        Sentence sentenceByNumber = text.getSentenceByNumber(sentenceNumber);

        assertThat(sentenceByNumber.getTokenList(), equalTo(sentenceFromList.getTokenList()));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSentenceWithNumberLessThanZeroShouldThrowException() throws Exception {
        text.getSentenceByNumber(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSentenceWithNumberGreaterThanOrEqualToSentenceCountShouldThrowException() throws Exception {
        text.getSentenceByNumber(text.getSentenceCount());
    }

    @Test
    public void updateSentenceReturnsNewText() throws Exception {
        Text updatedSentenceText = text.updateSentence(sentenceNumber, sentenceList.get(sentenceNumber));

        assertThat(text, not(sameInstance(updatedSentenceText)));
    }

    @Test
    public void updateSentence() throws Exception {
        Sentence beforeUpdateSentence = text.getSentenceByNumber(sentenceNumber);

        List<Token> tokens = Stream.of("ccc", ".")
                .map(factory::createToken)
                .collect(Collectors.toList());
        Sentence updatedSentence = new Sentence(tokens);

        Text updatedSentenceText = text.updateSentence(sentenceNumber, updatedSentence);

        assertThat(updatedSentenceText.getSentenceList(), allOf(
                hasItem(updatedSentence),
                not(hasItem(beforeUpdateSentence))));
    }

    @Test
    public void getSentenceListReturnsNewInstance() throws Exception {
        List<Sentence> firstCallList = text.getSentenceList();
        List<Sentence> secondCallList = text.getSentenceList();

        assertThat(firstCallList, allOf(
                equalTo(secondCallList),
                not(sameInstance(secondCallList))));
    }

    @Test
    public void toStringReturnsConcatenatedSentencesDelimitedWithWhitespaces() throws Exception {
        String textString = text.toString();

        String expectedString = sentenceList.stream()
                .map(Sentence::toString)
                .collect(Collectors.joining(" "));

        assertThat(textString, equalTo(expectedString));
    }
}