package org.vitaly.textProcessing.parser;

import org.vitaly.textProcessing.model.Sentence;
import org.vitaly.textProcessing.model.Text;
import org.vitaly.textProcessing.model.Token;
import org.vitaly.textProcessing.model.TokenFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.vitaly.textProcessing.model.PunctuationMark.END_OF_SENTENCE_REGEX;
import static org.vitaly.textProcessing.model.PunctuationMark.PUNCTUATION_REGEX;
import static org.vitaly.textProcessing.util.InputChecker.STRING;
import static org.vitaly.textProcessing.util.InputChecker.requireNonNull;

/**
 * Created by vitaly on 2017-03-19.
 */
public class TextParser {
    private TokenFactory factory;
    private String whitespaceRegex;

    public TextParser() {
        factory = new TokenFactory();
        whitespaceRegex = "\\s+";
    }

    public Text parseText(String string) {
        requireNonNull(string, STRING);

        SavingStrategy sentenceSplittingSaveStrategy = new AppendToCurrentTokenStrategy();

        List<String> sentenceStrings = splitTokenStrings(string, END_OF_SENTENCE_REGEX, sentenceSplittingSaveStrategy);

        String punctuationAndWhitespaceRegex = PUNCTUATION_REGEX + "|" + whitespaceRegex;
        SavingStrategy wordSplittingSaveStrategy = new SaveAsSeparateTokenStrategy();

        List<Sentence> sentenceList = sentenceStrings.stream()
                .map(x -> splitTokenStrings(x, punctuationAndWhitespaceRegex, wordSplittingSaveStrategy))
                .map(this::parseSentenceTokens)
                .map(Sentence::new)
                .collect(Collectors.toList());

        return new Text(sentenceList);
    }

    public List<String> splitTokenStrings(String string, String regex, SavingStrategy savingStrategy) {
        requireNonNull(string, STRING);
        requireNonNull(string, "Regular expression");
        requireNonNull(savingStrategy, "Saving strategy");

        String leftover = string;
        String[] splitResult = leftover.split(regex, 2);

        List<String> result = new ArrayList<>();
        while (splitResult.length == 2) {
            String currentToken = splitResult[0];

            char splitCharacter = leftover.charAt(currentToken.length());
            requireOneSymbolSplitter(leftover, splitResult, splitCharacter);

            savingStrategy.save(currentToken, String.valueOf(splitCharacter), result);

            leftover = splitResult[1].trim();
            splitResult = leftover.split(regex, 2);
        }
        if (splitResult.length == 1 && !splitResult[0].trim().isEmpty()) {
            savingStrategy.saveTrimmed(splitResult[0], result);
        }
        return result;
    }

    private void requireOneSymbolSplitter(String origin, String[] splitResult, char splitCharacter) {
        int restoredLength = splitResult[0].length() + splitResult[1].length() + 1;
        if (!String.valueOf(splitCharacter).matches(whitespaceRegex) && restoredLength != origin.length()) {
            throw new IllegalArgumentException(
                    "Splitter string is longer than one character and is not a whitespace sequence!");
        }
    }

    public List<Token> parseSentenceTokens(List<String> stringList) {
        requireNonNull(stringList, "String list");

        return stringList.stream()
                .map(factory::createToken)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        TextParser parser = new TextParser();
        parser.requireOneSymbolSplitter("aaa,bbb", new String[]{"aaa", "bbb"}, ',');
    }
}
