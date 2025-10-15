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

    public BigInteger calculate(Stream<CalculationElement> tokens) {
        CalculationResult calculationResult = CalculationResult.of(BigInteger.ZERO);

        if (elements.isEmpty()) {
            return calculationResult.getValue();
        }

        CalculationResult result = getFirstOperand();
        CalculationElement previous = createDummyOperand();

        while (hasMoreElements()) {
            CalculationElement current = elements.poll();
            result = processElement(current, result); // CalculationResult 반환
            previous = current;
        }

        return calculationResult.getValue();
    }

    private CalculationResult processElement(CalculationElement current, CalculationResult calculationResult) {
        if (current != null && current.isOperator()) {
            return processOperator(calculationResult);
        }
        throw new IllegalStateException("예상치 못한 피연산자");
    }

    private CalculationResult processOperator(CalculationResult calculationResult) {
        validateHasNextOperand();

        CalculationElement nextElement = getNextElement();
        validateIsOperand(nextElement);

        BigInteger operand = ((Operand) nextElement).getOperand();

        return calculationResult.plus(operand);
    }

    private boolean hasMoreElements() {
        return !elements.isEmpty();
    }

    private CalculationElement getNextElement() {
        return elements.poll();
    }

    private void validateHasNextOperand() {
        if (!hasMoreElements()) {
            throw new IllegalArgumentException("연산자 다음에는 숫자가 와야 합니다");
        }
    }

    private static Queue<CalculationElement> parseToQueue(Stream<CalculationElement> tokens) {
        Queue<CalculationElement> calculateQueue = new LinkedList<>();
        tokens.forEach(calculateQueue::add);
        return calculateQueue;
    }

    private CalculationResult getFirstOperand() {
        CalculationElement first = elements.poll();
        validateIsOperand(first);

        BigInteger value = ((Operand) first).getValue();
        return CalculationResult.of(value);
    }

    private void validateIsOperand(CalculationElement element) {
        if(element == null || !element.isOperand()) {
            throw new IllegalArgumentException("계산식은 숫자로 시작해야 한다.");
        }
    }

    private static CalculationElement createDummyOperand() {
        return Operand.from("0");
    }
}
