package org.vitaly.textProcessing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.vitaly.textProcessing.util.InputChecker.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Text {
    private List<Sentence> sentenceList;

    public Text(List<Sentence> sentenceList) {
        requireNonEmptyList(sentenceList, "Sentence list");

        this.sentenceList = new ArrayList<>(sentenceList);
    }

    public List<Sentence> getSentenceList() {
        return new ArrayList<>(sentenceList);
    }

    public int getSentenceCount() {
        return sentenceList.size();
    }

    public Sentence getSentenceByNumber(int number) {
        requireInRange(number, 0, sentenceList.size(), "Sentence number");

        return sentenceList.get(number);
    }

    public Text updateSentence(int number, Sentence updatedSentence) {
        requireInRange(number, 0, sentenceList.size(), "Sentence number");
        requireNonNull(updatedSentence, "Updated sentence");

        List<Sentence> newSentences = new ArrayList<>(sentenceList);

        newSentences.set(number, updatedSentence);

        return new Text(newSentences);
    }

    @Override
    public String toString() {
        return sentenceList.stream()
                .map(Sentence::toString)
                .collect(Collectors.joining(" "));
    }
}
