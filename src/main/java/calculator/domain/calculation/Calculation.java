package calculator.domain.calculation;

import calculator.domain.tokenizing.CalculationElement;
import calculator.domain.tokenizing.Operand;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class Calculation {

    private final Queue<CalculationElement> elements;

    private Calculation(Queue<CalculationElement> elements) {
        this.elements = elements;
    }

    public static Calculation from(Stream<CalculationElement> tokens) {
        return new Calculation(parseToQueue(tokens));
    }

    public BigInteger calculate() {
        CalculationResult result = CalculationResult.of(BigInteger.ZERO);
        if (!hasMoreElements()) {
            return result.getValue();
        }

        result = getFirstOperand();
        while (hasMoreElements()) {
            CalculationElement current = elements.poll();
            result = processElement(current, result);
        }

        return result.getValue();
    }

    private boolean hasMoreElements() {
        return !elements.isEmpty();
    }

    private CalculationResult getFirstOperand() {
        CalculationElement first = elements.poll();
        requireFirstCalculationElementIsOperand(first);

        BigInteger value = ((Operand) first).getValue();
        return CalculationResult.of(value);
    }

    private void requireFirstCalculationElementIsOperand(CalculationElement element) {
        if(element == null || element.isOperator()) {
            throw new IllegalArgumentException("계산식은 숫자로 시작해야 한다.");
        }
    }

    private CalculationResult processElement(CalculationElement current, CalculationResult calculationResult) {
        if (current != null && !current.isOperand()) {
            return processOperator(calculationResult);
        }
        return calculationResult;
    }

    private CalculationResult processOperator(CalculationResult calculationResult) {
        requireOperatorFollowedByOperand();
        CalculationElement nextElement = getNextElement();
        requireOperand(nextElement);

        BigInteger operand = ((Operand) nextElement).getOperand();

        return calculationResult.plus(operand);
    }

    private void requireOperatorFollowedByOperand() {
        if (!hasMoreElements() && nextElementIsOperand()) {
            throw new IllegalArgumentException("연산자 다음에는 숫자가 와야 합니다.");
        }
    }

    private boolean nextElementIsOperand() {
        if(elements.isEmpty()) {
            return false;
        }
        return elements.peek().isOperand();
    }

    private CalculationElement getNextElement() {
        return elements.poll();
    }

    private void requireOperand(CalculationElement nextElement) {
        if(nextElement.isOperator()) {
            throw new IllegalArgumentException("피연산자 다음에는 연산자가 와야한다.");
        }
    }

    private static Queue<CalculationElement> parseToQueue(Stream<CalculationElement> tokens) {
        Queue<CalculationElement> calculateQueue = new LinkedList<>();
        tokens.forEach(calculateQueue::add);
        return calculateQueue;
    }
}
