package calculator.domain.calculation;

import calculator.domain.vo.CalculationElement;
import calculator.domain.vo.CalculationResult;
import calculator.domain.vo.Operand;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public class Calculation {

    private final Deque<CalculationElement> elements;

    private Calculation(Deque<CalculationElement> elements) {
        this.elements = elements;
    }

    public static Calculation from(Collection<CalculationElement> tokens) {
        return new Calculation(parseToDeque(tokens));
    }

    private static Deque<CalculationElement> parseToDeque(Collection<CalculationElement> tokens) {
        return new ArrayDeque<>(tokens);
    }

    public CalculationResult calculate() {
        CalculationResult result = initializeResultWithFirstOperand();
        while (hasMoreElements()) {
            CalculationElement current = elements.poll();
            result = processElement(current, result);
        }
        return result;
    }

    private CalculationResult initializeResultWithFirstOperand() {
        if (elements.peek() == null) {
            return CalculationResult.of(BigInteger.ZERO);
        }
        CalculationElement first = elements.poll();
        requireFirstCalculationElementIsOperand(first);

        BigInteger value = ((Operand) first).getValue();
        return CalculationResult.of(value);
    }

    private void requireFirstCalculationElementIsOperand(CalculationElement element) {
        if (element.isOperator()) {
            throw new IllegalArgumentException("계산식은 숫자로 시작해야 합니다.");
        }
    }

    private boolean hasMoreElements() {
        return !elements.isEmpty();
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
        BigInteger operand = ((Operand) nextElement).getValue();

        return calculationResult.plus(operand);
    }

    private void requireOperatorFollowedByOperand() {
        if (!hasMoreElements() || !nextElementIsOperand()) {
            throw new IllegalArgumentException("연산자 다음에는 피연산자가 와야 합니다.");
        }
    }

    private boolean nextElementIsOperand() {
        CalculationElement next = elements.peek();
        return next != null && next.isOperand();
    }

    private CalculationElement getNextElement() {
        return elements.poll();
    }
}
