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

/**
 * Created by vitaly on 2017-03-19.
 */
public class TextParser {
    private TokenFactory factory;

    public TextParser() {
        factory = new TokenFactory();
    }

    public Text parseText(String string) {
        List<String> sentenceStrings = splitTokenStrings(string, END_OF_SENTENCE_REGEX,
                new AppendToCurrentTokenStrategy());

        String whitespaceRegex = "\\s+";
        String punctuationAndWhitespaceRegex = PUNCTUATION_REGEX + "|" + whitespaceRegex;

        List<Sentence> sentenceList = sentenceStrings.stream()
                .map(x -> splitTokenStrings(x, punctuationAndWhitespaceRegex, new SaveAsSeparateTokenStrategy()))
                .map(this::parseSentenceTokens)
                .map(Sentence::new)
                .collect(Collectors.toList());

        return new Text(sentenceList);
    }

    private List<String> splitTokenStrings(String string, String regex, SavingStrategy savingStrategy) {
        String leftover = string;
        String[] splitResult = leftover.split(regex, 2);

        List<String> result = new ArrayList<>();
        while (splitResult.length == 2) {
            String currentToken = splitResult[0];

            //todo exception for longer than one
            char splitCharacter = leftover.charAt(currentToken.length());

            savingStrategy.save(currentToken, String.valueOf(splitCharacter), result);

            leftover = splitResult[1].trim();
            splitResult = leftover.split(regex, 2);
        }
        if (splitResult.length == 1 && !splitResult[0].trim().isEmpty()) {
            savingStrategy.saveTrimmed(splitResult[0], result);
        }
        return result;
    }

    public List<Token> parseSentenceTokens(List<String> stringList) {
        return stringList.stream()
                .map(this::parseToken)
                .collect(Collectors.toList());
    }

    public Token parseToken(String token) {
        return token.matches(PUNCTUATION_REGEX) ? factory.createPunctuation(token) : factory.createWord(token);
    }


    public static void main(String[] args) {
        System.out.println(
                new TextParser().parseText(
                        "16. В некотором предложении текста слова заданной длины заменить указанной подстрокой, " +
                                "длина которой может не совпадать с длиной слова."));
    }
}
