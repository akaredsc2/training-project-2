package org.vitaly.textProcessing.model;

import java.util.List;

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
}
