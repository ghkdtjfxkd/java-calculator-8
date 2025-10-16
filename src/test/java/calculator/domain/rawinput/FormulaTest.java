package calculator.domain.rawinput;

import static org.junit.jupiter.api.Assertions.*;

import static calculator.domain.rawinput.TestElements.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class FormulaTest {

    @Test
    @DisplayName("사용자의_입력이_null_이라면_예외를_발생시킨다.")
    void userInputIsNull() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> Formula.from(input));
    }

    @ParameterizedTest
    @MethodSource("provideCorrectFormats")
    @DisplayName("커스텀_구분자_지정_형식이_올바르다면_커스텀_구분자_후보를_반환해야_한다.")
    void correctCustomDelimiterFormat(String input) {
        Formula formula = Formula.from(input);

        String actual = formula.getCustomDelimiterCandidate().orElse(null);

        assertEquals(CORRECT_CANDIDATE.get(), actual);
    }

    @ParameterizedTest
    @MethodSource("provideWrongFormats")
    @DisplayName("커스텀_구분자_지정_형식이_올바르지_않다면_입력된_모든_문자열을_반환해야_한다.")
    void wrongCustomDelimiterFormatReturnRawInput(String input) {
        Formula formula = Formula.from(input);

        String actual = formula.getActualFormula();

        assertEquals(input, actual);
    }

    @ParameterizedTest
    @MethodSource("provideWrongFormats")
    @DisplayName("커스텀_구분자_지정_형식이_올바르지_않다면_커스텀_구분자_후보는_`null`_이어야_한다.")
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
