package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import calculator.domain.rawinput.Formula;
import calculator.domain.vo.CalculationElement;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TokensTest {

    @Test
    @DisplayName("사용자의_입력이_null_이라면_예외를_발생시킨다.")
    void userInputIsNullTest() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> Formula.from(input));
    }

    @Test
    @DisplayName("숫자나 구분자로 지정되지 않은 문자가 식에 포함되어 있다면 예외를 발생시켜야 한다.")
    void correctDelimiterTokenizeTest() {
        String input = "1,2,3|";
        assertThrows(IllegalArgumentException.class, () -> Tokens.from(input, Delimiters.defaults()));
    }

    @ParameterizedTest
    @MethodSource("provideMixedNumericAndCorrectDelimiterTokens")
    @DisplayName("문자열 속 숫자가 연달아 이어진다면 이어지는 숫자 문자열은 하나 토큰 단위어야 한다.")
    void numericTokenizeTest(String input) {
        List<CalculationElement> elements = getTokens(input);
        long tokensCount = elements.size();
        long operatorCount = getOperatorCount(elements);

        long expected = tokensCount - operatorCount;
        long actualTokenCount = getOperandCount(elements);

        assertEquals(expected, actualTokenCount);
    }

    private List<CalculationElement> getTokens(String input) {
        return Tokens.from(input, Delimiters.defaults())
                .getTokensStream()
                .toList();
    }

    private long getOperandCount(List<CalculationElement> elements) {
        return elements.stream()
                .filter(CalculationElement::isOperand)
                .count();
    }

    private long getOperatorCount(List<CalculationElement> elements) {
        return elements.stream()
                .filter(CalculationElement::isOperator)
                .count();
    }

    private static Stream<String> provideMixedNumericAndCorrectDelimiterTokens() {
        return Stream.of(
                "1",
                "123456",
                "0012,0",
                "1,",
                "1,2,3,4",
                "12,33,4,",
                "123,:,4",
                "1,2,3,",
                ",:,"
        );
    }
}
