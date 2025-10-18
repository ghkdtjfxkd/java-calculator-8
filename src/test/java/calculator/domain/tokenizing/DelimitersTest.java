package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DelimitersTest {

    @Test
    @DisplayName("기본_구분자는_콤마(,)와_콜론(:)을 가져야 한다.")
    void defaultDelimitersHasCommaAndColon() {
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
        String customDelimiter = "/";
        Delimiters delimiters = Delimiters.withCustom(customDelimiter);

        assertTrue(delimiters.has(customDelimiter.charAt(0)));
    }

    @Test
    @DisplayName("구분자는 숫자가 아니어야 한다.")
    void numericDelimiterTest() {
        String numericDelimiter = "1";
        assertThrows(IllegalArgumentException.class, () -> Delimiters.withCustom(numericDelimiter));
    }
}
