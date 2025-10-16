package calculator.domain.tokenizing;

import calculator.domain.delimiter.Delimiters;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Tokens {

    public static Tokens of(String formula, Delimiters delimiters) {
        return new Tokens(parseToQueue(formula, delimiters));
    }

    private final Queue<CalculationElement> tokens;

    private Tokens(Queue<CalculationElement> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    private static Queue<CalculationElement> parseToQueue(String formula, Delimiters delimiters) {
        Queue<CalculationElement> elements = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (char currentChar : formula.toCharArray()) {
            requireRecognizedCalculationElement(currentChar, delimiters);
            if(isNotDelimiter(currentChar, delimiters)) {
                stringBuilder.append(currentChar);
                continue;
            }
            offerStackedOperandBuffer(stringBuilder, elements);
            offerOperator(currentChar, elements);
        }
        offerStackedOperandBuffer(stringBuilder, elements);
        return elements;
    }

    private static void requireRecognizedCalculationElement(char currentChar ,Delimiters delimiters) {
        if(isUnrecognizedSymbol(currentChar, delimiters)) {
            throw new IllegalArgumentException("식에 올바르지 않은 문자가 섞여있습니다. 처음으로 식별된 문자: [ " + currentChar + " ]");
        }
    }

    private static boolean isUnrecognizedSymbol(char currentChar, Delimiters delimiters) {
        return isNotNumeric(currentChar) && isNotDelimiter(currentChar, delimiters);
    }

    private static boolean isNotNumeric(char currentChar) {
        return !Character.isDigit(currentChar);
    }

    private static boolean isNotDelimiter(char currentChar, Delimiters delimiters) {
        return !delimiters.has(currentChar);
    }

    private static void offerOperator(char currentChar, Queue<CalculationElement> elements) {
        String value = Character.toString(currentChar);
        elements.offer(CalculationElement.from(value));
    }

    private static void offerOperand(StringBuilder stringBuilder, Queue<CalculationElement> elements) {
        String token = stringBuilder.toString().trim();
        if(!token.isEmpty()) {
            elements.offer(CalculationElement.from(token));
        }
    }

    private static void offerStackedOperandBuffer(StringBuilder stringBuilder, Queue<CalculationElement> elements) {
        offerOperand(stringBuilder, elements);
        stringBuilder.setLength(0);
    }

    public Stream<CalculationElement> stream() {
        return tokens.stream();
    }
}
