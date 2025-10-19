package calculator.domain.tokenizing;

import calculator.domain.vo.CalculationElement;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class Tokens {

    private final Deque<CalculationElement> tokens;

    private Tokens(Deque<CalculationElement> tokens) {
        this.tokens = tokens;
    }

    public static Tokens from(String formula, Delimiters delimiters) {
        return new Tokens(parseToDeque(formula, delimiters));
    }

    private static Deque<CalculationElement> parseToDeque(String formula, Delimiters delimiters) {
        Deque<CalculationElement> elements = new ArrayDeque<>();
        StringBuilder buffer = new StringBuilder();
        for (int index = 0; index < formula.length(); index++) {
            char currentChar = formula.charAt(index);
            requireRecognizedCalculationElement(currentChar, delimiters);
            if (isNotDelimiter(currentChar, delimiters)) {
                buffer.append(currentChar);
                continue;
            }
            offerStackedOperandBuffer(buffer, elements);
            offerOperator(currentChar, elements);
        }
        offerStackedOperandBuffer(buffer, elements);
        return elements;
    }

    private static void requireRecognizedCalculationElement(char currentChar, Delimiters delimiters) {
        if (isUnrecognizedSymbol(currentChar, delimiters)) {
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

    private static void offerStackedOperandBuffer(StringBuilder buffer, Queue<CalculationElement> elements) {
        offerOperand(buffer, elements);
        buffer.setLength(0);
    }

    private static void offerOperand(StringBuilder buffer, Queue<CalculationElement> elements) {
        String token = buffer.toString().trim();
        if (!token.isEmpty()) {
            elements.offer(CalculationElement.of(token));
        }
    }

    private static void offerOperator(char currentChar, Queue<CalculationElement> elements) {
        elements.offer(CalculationElement.of(currentChar));
    }

    public Deque<CalculationElement> getTokens() {
        return new ArrayDeque<>(tokens);
    }
}
