package calculator.domain.tokenizing;

import calculator.domain.delimiter.Delimiters;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Tokens {

    private final Queue<CalculationElement> tokens;

    private Tokens(Queue<CalculationElement> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    public static Tokens from(String formula, Delimiters delimiters) {
        return new Tokens(parseToQueue(formula, delimiters));
    }

    public Stream<CalculationElement> getTokensStream() {
        return tokens.stream();
    }

    private static Queue<CalculationElement> parseToQueue(String formula, Delimiters delimiters) {
        Queue<CalculationElement> elements = new LinkedList<>();
        StringBuilder buffer = new StringBuilder();
        for (char currentChar : formula.toCharArray()) {
            requireRecognizedCalculationElement(currentChar, delimiters);
            if(isNotDelimiter(currentChar, delimiters)) {
                buffer.append(currentChar);
                continue;
            }
            offerStackedOperandBuffer(buffer, elements);
            offerOperator(currentChar, elements);
        }
        offerStackedOperandBuffer(buffer, elements);
        return elements;
    }

    private static void requireRecognizedCalculationElement(char currentChar ,Delimiters delimiters) {
        if(isUnrecognizedSymbol(currentChar, delimiters)) {
            throw new IllegalArgumentException("식에 올바르지 않은 문자가 섞여있습니다. 최초로 식별된 올바르지 않은 문자: [ " + currentChar + " ]");
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
        elements.offer(CalculationElement.of(value));
    }

    private static void offerStackedOperandBuffer(StringBuilder buffer, Queue<CalculationElement> elements) {
        offerOperand(buffer, elements);
        buffer.setLength(0);
    }

    private static void offerOperand(StringBuilder buffer, Queue<CalculationElement> elements) {
        String token = buffer.toString().trim();
        if(!token.isEmpty()) {
            elements.offer(CalculationElement.of(token));
        }
    }
}
