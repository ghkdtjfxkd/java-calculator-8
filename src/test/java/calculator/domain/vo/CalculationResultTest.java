package calculator.domain.vo;

import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.calculation.Calculation;
import java.math.BigInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CalculationResultTest {

    @Test
    @DisplayName("계산 결과를 만드는 값이 null 일 경우 예외가 발생해야한다.")
    void nonNullCalculationResultTest() {
        assertThrows(IllegalArgumentException.class ,() -> CalculationResult.of(null));
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
}
