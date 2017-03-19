package org.vitaly.textProcessing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.vitaly.textProcessing.util.InputChecker.requireNonEmptyList;
import static org.vitaly.textProcessing.util.InputChecker.requireNonNull;

/**
 * Created by vitaly on 2017-03-19.
 */
public class Sentence {
    private List<Token> tokenList;

    public Sentence(List<Token> tokenList) {
        requireNonEmptyList(tokenList, "Token list");

        this.tokenList = tokenList;
    }

    public Sentence replaceTokens(Predicate<Token> predicate, Token substitute) {
        requireNonNull(predicate, "Predicate");
        requireNonNull(substitute, "Substitute");

        List<Token> newSentenceTokens = new ArrayList<>(tokenList);

        for (int i = 0; i < newSentenceTokens.size(); i += 1) {
            if (predicate.test(newSentenceTokens.get(i))) {
                newSentenceTokens.set(i, substitute);
            }
        }

        return new Sentence(newSentenceTokens);
    }

    public List<Token> getTokenList() {
        return new ArrayList<>(tokenList);
    }

    @Override
    public String toString() {
        return tokenList.stream()
                .map(Token::toString)
                .collect(Collectors.joining(" "));
    }
}
