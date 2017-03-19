package org.vitaly.textProcessing.processor;

import org.vitaly.textProcessing.model.Sentence;
import org.vitaly.textProcessing.model.Text;
import org.vitaly.textProcessing.model.Token;
import org.vitaly.textProcessing.model.TokenFactory;

import java.util.function.Predicate;

import static org.vitaly.textProcessing.util.InputChecker.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class TextProcessor {
    private TokenFactory factory;

    public TextProcessor() {
        factory = new TokenFactory();
    }

    public Text replaceWordsInSentence(Text text, int sentenceNumber, int targetWordLength, String substitute) {
        requireNonNull(text, "Text");
        requireInRange(sentenceNumber, 0, text.getSentenceCount(), "Sentence number");
        requireInRange(targetWordLength, 0, Integer.MAX_VALUE, "Target word length");
        requireNonEmptyString(substitute, "Substitution string");

        Sentence sentence = text.getSentenceByNumber(sentenceNumber);
        Token substituteToken = factory.createToken(substitute);
        Predicate<Token> tokenPredicate = token -> token.isWord() && token.getLength() == targetWordLength;
        Sentence updatedSentence = sentence.replaceTokens(tokenPredicate, substituteToken);
        return text.updateSentence(sentenceNumber, updatedSentence);
    }
}
