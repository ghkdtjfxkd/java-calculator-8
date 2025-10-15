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

    public static Tokens of(String formula, Delimiters delimiters) {
        return new Tokens(parseToQueue(formula, delimiters));
    }

    private static Queue<CalculationElement> parseToQueue(String formula, Delimiters delimiters) {
        Queue<CalculationElement> elements = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (char currentChar : formula.toCharArray()) {
            if(!delimiters.has(currentChar)) {
                stringBuilder.append(currentChar);
                continue;
            }
            offerStackedOperandBuffer(stringBuilder, elements);
            elements.offer(CalculationElement.from(String.valueOf(currentChar)));
        }
        offerStackedOperandBuffer(stringBuilder, elements);
        return elements;
    }

    public Stream<CalculationElement> stream() {
        return tokens.stream();
    }

    private static void offerStackedOperandBuffer(StringBuilder stringBuilder, Queue<CalculationElement> elements) {
        offerOperand(stringBuilder, elements);
        stringBuilder.setLength(0);
    }

    private static void offerOperand(StringBuilder stringBuilder, Queue<CalculationElement> elements) {
        String token = stringBuilder.toString().trim();
        if(!token.isEmpty()) {
            elements.offer(CalculationElement.from(token));
        }
    }
}
