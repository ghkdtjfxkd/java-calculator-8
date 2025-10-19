package calculator.domain.rawinput;

import static calculator.domain.rawinput.TestElements.CORRECT_CANDIDATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class FormulaTest {

    @Test
    @DisplayName("사용자의 입력이 null 이라면 예외를 발생시킨다.")
    void userInputIsNull() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> Formula.from(input));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectFormats")
    @DisplayName("커스텀 구분자 지정 형식이 올바르다면 커스텀 구분자 후보를 반환해야 한다.")
    void correctCustomDelimiterFormat(String input) {
        Formula formula = Formula.from(input);

        String actual = formula.getCustomDelimiterCandidate().orElse(null);

        assertEquals(CORRECT_CANDIDATE.get(), actual);
    }

    @ParameterizedTest
    @MethodSource("provideWrongFormats")
    @DisplayName("커스텀 구분자 지정 형식이 올바르지 않다면 계산식으로 입력된 모든 문자열을 반환해야 한다.")
    void wrongCustomDelimiterFormatReturnRawInput(String input) {
        Formula formula = Formula.from(input);

        String actual = formula.getActualFormula();

        assertEquals(input, actual);
    }

    @ParameterizedTest
    @MethodSource("provideWrongFormats")
    @DisplayName("커스텀 구분자 지정 형식이 올바르지 않다면 커스텀 구분자 후보는 `null` 이어야한다.")
    void wrongCustomDelimiterFormat(String input) {
        Formula formula = Formula.from(input);

        String actual = formula.getCustomDelimiterCandidate().orElse(null);

        assertNull(actual);
    }

    private static Stream<String> provideCorrectFormats() {
        return TestcaseMethods.provideCorrectFormats();
    }

    private static Stream<String> provideWrongFormats() {
        return TestcaseMethods.provideWrongFormats();
    }
}
