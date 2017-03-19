package org.vitaly.textProcessing.parser;

import org.vitaly.textProcessing.model.PunctuationMark;
import org.vitaly.textProcessing.model.Sentence;
import org.vitaly.textProcessing.model.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.vitaly.textProcessing.model.PunctuationMark.*;

/**
 * Created by vitaly on 2017-03-19.
 */
public class TextParser {
    public Text parseText(String string) {
        List<String> sentenceStrings = splitSavingSplitter(string, END_OF_SENTENCE_REGEX,
                new AppendToCurrentTokenStrategy());
        System.out.println(sentenceStrings);

        String whitespaceRegex = "\\s+";
        String punctuationAndWhitespaceRegex = PUNCTUATION_REGEX + "|" + whitespaceRegex;

        List<List<String>> collect = sentenceStrings.stream()
                .map(x -> splitSavingSplitter(x, punctuationAndWhitespaceRegex, new SaveAsSeparateTokenStrategy()))
                .collect(Collectors.toList());

        System.out.println(collect);

        return null;
    }

    private List<String> splitSavingSplitter(String string, String regex, SavingStrategy savingStrategy) {
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

    public static void main(String[] args) {
        new TextParser().parseText("a- bsdfsd-gfhfg c. (d) e- f! g h -i? j- k l");
    }
}
