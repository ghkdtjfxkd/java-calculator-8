package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DelimitersTest {

    @Test
    @DisplayName("기본 구분자는 콤마(,)와 콜론(:)을 가져야 한다.")
    void defaultDelimitersHasCommaAndColonTest() {
        Delimiters defaults = Delimiters.defaults();
        String comma = ",";
        String colon = ":";

        boolean defaultDelimitersContainComma = defaults.has(comma.charAt(0));
        boolean defaultDelimitersContainColon = defaults.has(colon.charAt(0));

        assertTrue(defaultDelimitersContainComma && defaultDelimitersContainColon);
    }

    @Test
    @DisplayName("커스텀 구분자를 추가할 수 있다.")
    void addCustomDelimiterTest() {
        String customDelimiter = ".";
        Delimiters delimiters = Delimiters.withCustom(customDelimiter);

        assertTrue(delimiters.has(customDelimiter.charAt(0)));
    }

    @ParameterizedTest(name = "[{index}] 숫자로 된 구분자 테스트: {0}")
    @ValueSource(strings = {"1", "123", "0", "012"})
    @DisplayName("구분자는 숫자가 아니어야 한다.")
    void numericDelimiterTest(String numericDelimiter) {
        assertThrows(IllegalArgumentException.class, () -> Delimiters.withCustom(numericDelimiter));
    }
}
