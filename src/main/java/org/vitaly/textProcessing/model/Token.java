package org.vitaly.textProcessing.model;

/**
 * Created by vitaly on 2017-03-19.
 */
public interface Token {
    boolean isWord();

    boolean isPunctuationMark();

    int getLength();
}
