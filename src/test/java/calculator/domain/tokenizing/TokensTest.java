package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.delimiter.Delimiters;
import calculator.domain.rawinput.Formula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokensTest {

    @Test
    @DisplayName("사용자의_입력이_null_이라면_예외를_발생시킨다.")
    void userInputIsNull() {
        String input = null;
        assertThrows(IllegalArgumentException.class, () -> Formula.from(input));
    }

    @Test
    @DisplayName("숫자나 구분자로 지정되지 않은 문자가 식에 포함되어 있다면 예외를 발생시켜야 한다.")
    void correctDelimiterTokenizeTest() {
        String input = "1,2,3|";
        assertThrows(IllegalArgumentException.class, () -> Tokens.of(input, Delimiters.defaults()));
    }
}
