package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import calculator.domain.vo.CalculationElement;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TokensTest {

    @Test
    @DisplayName("빈 계산식이 입력된다면 예외를 발생시키지 말아야 한다.")
    void emptyFormulaExceptionTest() {
        String input = "";
        assertDoesNotThrow(() -> Tokens.from(input, Delimiters.defaults()));
    }

    @Test
    @DisplayName("숫자나 구분자로 지정되지 않은 문자(공백문자 \"\"제외)가 식에 포함되어 있다면 예외를 발생시켜야 한다.")
    void correctDelimiterTokenizeTest() {
        String input = "1,2,3|";
        assertThrows(IllegalArgumentException.class, () -> Tokens.from(input, Delimiters.defaults()));
    }

    @ParameterizedTest
    @MethodSource("provideMixedNumericAndCorrectDelimiterTokens")
    @DisplayName("문자열 속 숫자가 연달아 이어진다면 이어지는 숫자 문자열은 하나 토큰 단위어야 한다.")
    void numericTokenizeTest(String input) {
        Collection<CalculationElement> elements = getTokens(input);
        long tokensCount = elements.size();
        long operatorCount = getOperatorCount(elements);

        long expected = tokensCount - operatorCount;
        long actualTokenCount = getOperandCount(elements);

        assertEquals(expected, actualTokenCount);
    }

    private Collection<CalculationElement> getTokens(String input) {
        return Tokens.from(input, Delimiters.defaults())
                .getTokens();
    }

    private long getOperandCount(Collection<CalculationElement> elements) {
        return elements.stream()
                .filter(CalculationElement::isOperand)
                .count();
    }

    private long getOperatorCount(Collection<CalculationElement> elements) {
        return elements.stream()
                .filter(CalculationElement::isOperator)
                .count();
    }

    private static Stream<String> provideMixedNumericAndCorrectDelimiterTokens() {
        return Stream.of(
                "",
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
