package calculator.domain.calculation;

import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.vo.CalculationElement;
import calculator.domain.vo.CalculationResult;
import calculator.domain.vo.Operand;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CalculationTest {

    @Test
    @DisplayName("계산 요소 큐가 비어있을 때, 계산을 시도한다면 0을 반환한다.")
    void vacantQueueCalculationTest() {
        Stream<CalculationElement> vacantStream = Stream.of();
        Calculation calculation = Calculation.from(vacantStream);
        CalculationResult calculationResult = calculation.calculate();

        BigInteger expected = BigInteger.ZERO;
        BigInteger actual = calculationResult.getValue();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideOnlyOneOperandTokens")
    @DisplayName("계산 요소 큐에 피연산자 하나만 들어있을 경우, 계산을 시도한다면 그 피연산자의 값을 반환한다.")
    void onlyOneOperandTest(Operand operand) {
        Stream<CalculationElement> onlyOneOperand = Stream.of(operand);
        Calculation calculation = Calculation.from(onlyOneOperand);
        CalculationResult calculationResult = calculation.calculate();

        BigInteger expected = operand.getValue();
        BigInteger actual = calculationResult.getValue();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideCorrectSequences")
    @DisplayName("계산 요소의 순서가 올바르다면, 계산 결과를 반환해야 한다.")
    void correctSequenceTokensTest(Stream<CalculationElement> tokens) {
        List<CalculationElement> correctSequence = tokens.toList();
        Calculation calculation = Calculation.from(correctSequence.stream());
        CalculationResult calculationResult = calculation.calculate();

        BigInteger expected = correctResult(correctSequence);
        BigInteger actual = calculationResult.getValue();

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideWrongSequences")
    @DisplayName("계산 요소의 순서가 올바르지 않다면, 예외를 발생시켜야 한다.")
    void wrongSequenceTokensTest(Stream<CalculationElement> tokens) {
        Calculation calculation = Calculation.from(tokens);

        assertThrows(IllegalArgumentException.class, calculation::calculate);
    }

    private BigInteger correctResult(List<CalculationElement> tokens) {
        return tokens.stream()
                .filter(CalculationElement::isOperand)
                .map(calculationElement -> (Operand) calculationElement)
                .map(Operand::getValue)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private static Stream<Operand> provideOnlyOneOperandTokens() {
        return TestcaseMethods.provideOnlyOneOperandTokens();
    }

    private static Stream<Stream<CalculationElement>> provideCorrectSequences() {
        return TestcaseMethods.provideCorrectSequences();
    }

    private static Stream<Stream<CalculationElement>> provideWrongSequences() {
        return TestcaseMethods.provideWrongSequences();
    }
}
