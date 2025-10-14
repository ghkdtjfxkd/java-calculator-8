package calculator.domain.rawinput;

import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.delimiter.Delimiters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DelimitersTest {

    @Test
    @DisplayName("기본_구분자는_콤마(,)와_콜론(:)을 가져야 한다.")
    void defaultDelimitersHasCommaAndColon() {
        Delimiters defaults = Delimiters.defaults();
        String comma = ",";
        String colon = ":";

        boolean defaultDelimitersContainComma = defaults.has(comma);
        boolean defaultDelimitersContainColon = defaults.has(colon);

        assertTrue(defaultDelimitersContainComma && defaultDelimitersContainColon);
    }

    @Test
    @DisplayName("커스텀_구분자를_추가할_수_있다.")
    void addCustomDelimiter() {
        String customDelimiter = "/";
        Delimiters delimiters = Delimiters.defaults();
        delimiters = delimiters.withCustom(customDelimiter);

        boolean delimitersContainCustomDelimiter = delimiters.has(customDelimiter);

        assertTrue(delimitersContainCustomDelimiter);
    }

    @Test
    @DisplayName("구분자는_숫자가_아니어야_한다.")
    void numericDelimiter() {
        String customDelimiter = "1";
        Delimiters delimiters = Delimiters.defaults();

        assertThrows(IllegalArgumentException.class, () -> delimiters.withCustom(customDelimiter));
    }
}
