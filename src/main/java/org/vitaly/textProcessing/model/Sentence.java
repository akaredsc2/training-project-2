package org.vitaly.textProcessing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Sentence {
    private List<Token> tokenList;

    public Sentence(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public Sentence replaceTokensMatchingPredicate(Predicate<Token> predicate, Token substitute) {
        List<Token> newSentenceTokens = new ArrayList<>(tokenList.size());

        Collections.copy(newSentenceTokens, this.tokenList);

        for (int i = 0; i < newSentenceTokens.size(); i += 1) {
            if (predicate.test(newSentenceTokens.get(i))) {
                newSentenceTokens.set(i, substitute);
            }
        }

        return new Sentence(newSentenceTokens);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        for (Token token : tokenList) {
            joiner.add(token.toString());
        }
        return joiner.toString();
    }
}
