package org.vitaly.textProcessing.model;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Text {
    private List<Sentence> sentenceList;

    public Text(List<Sentence> sentenceList) {
        this.sentenceList = sentenceList;
    }

    public Sentence getSentenceByNumber(int number) {
        return sentenceList.get(number);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        for (Sentence sentence : sentenceList) {
            joiner.add(sentence.toString());
        }
        return joiner.toString();
    }
}
