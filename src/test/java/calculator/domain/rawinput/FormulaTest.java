package calculator.domain.rawinput;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormulaTest {

    private final String correctLeftBracket = "//";
    private final String correctRightBracket = "\\n";
    private final String correctCustomDelimiterCandidate  = ".";
    private final String actualFormula = "1,2,3";

    @Test
    @DisplayName("사용자의_입력이_비어_있다면_예외를_발생시킨다.")
    void userInputIsBlankOrNull() {
        String input = "";
        assertThrows(IllegalArgumentException.class,  () -> Formula.from(input));
    }

    @Test
    @DisplayName("사용자의_입력이_null_이라면_예외를_발생시킨다.")
    void userInputIsNull() {
        String input = null;
        assertThrows(IllegalArgumentException.class,  () -> Formula.from(input));
    }

    @Test
    @DisplayName("커스텀_구분자_지정_형식이_올바르다면_계산식_추출_시_커스텀_구분자_지정_부분이_없는_문자열을_반환해야_한다.")
    void correctCustomDelimiterFormatReturnActualFormula() {
        String input = correctLeftBracket + correctCustomDelimiterCandidate + correctRightBracket + actualFormula;
        Formula formula = Formula.from(input);

        String expected = actualFormula;
        String actual = formula.getActualFormula();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("커스텀_구분자_지정_형식이_올바르다면_커스텀_구분자_후보를_반환해야_한다.")
    void correctCustomDelimiterFormat() {
        String input = correctLeftBracket + correctCustomDelimiterCandidate + correctRightBracket;
        Formula formula = Formula.from(input);

        String expected = correctCustomDelimiterCandidate;
        String actual = formula.getCustomDelimiterCandidate();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("커스텀_구분자_지정_형식이_올바르지_않다면_입력된_모든_문자열을_반환해야_한다.")
    void wrongCustomDelimiterFormatReturnRawInput() {
        String rawInput = correctLeftBracket + correctCustomDelimiterCandidate+ actualFormula;
        Formula formula = Formula.from(rawInput);

        String expected = rawInput;
        String actual = formula.getActualFormula();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("커스텀_구분자_지정_형식이_올바르지_않다면_커스텀_구분자_후보는_`null`_이어야_한다.")
    void wrongCustomDelimiterFormat() {
        String input = actualFormula;
        Formula formula = Formula.from(input);

        String actual = formula.getCustomDelimiterCandidate();

        assertNull(actual);
    }


}