package calculator.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(IllegalArgumentException.class, () -> CalculationResult.of(null));
    }

    @Test
    @DisplayName("기존 계산 결과에 더하려는 값이 null이라면 예외가 발생해야 한다.")
    void provideNullOperandsTest() {
        CalculationResult previous = CalculationResult.of(BigInteger.ZERO);
        assertThrows(IllegalArgumentException.class, () -> previous.plus(null));
    }

    @ParameterizedTest
    @MethodSource("provideOperands")
    @DisplayName("기존 계산 결과에 값이 정상적으로 더해져야 한다.")
    void calculationResultPlusTest(BigInteger operand) {
        CalculationResult previous = CalculationResult.of(BigInteger.ZERO);

        BigInteger expected = operand.add(previous.getValue());
        BigInteger actual = previous.plus(operand).getValue();
        assertEquals(expected, actual);
    }

    private static Stream<BigInteger> provideOperands() {
        return Stream.of(
                BigInteger.ZERO,
                BigInteger.ONE,
                new BigInteger("9223372036854775808") // 9223372036854775807(Long.MAX_VALUE) + 1
        );
    }
}
