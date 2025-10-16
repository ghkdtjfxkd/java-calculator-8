package calculator.domain.rawinput;

import static calculator.domain.rawinput.TestElements.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CustomDelimiterSectionTest {

    private static Stream<String> provideCorrectFormats() {
        return TestcaseMethods.provideCorrectFormats();
    }

    private static Stream<String> provideWrongFormats() {
        return TestcaseMethods.provideWrongFormats();
    }

    @ParameterizedTest
    @MethodSource("provideCorrectFormats")
    @DisplayName("입력된 문자열이 올바른 커스텀 구분자 지정 형식으로 시작한다면, 올바른 커스텀 구분자 후보를 반환해야한다.")
    void inputStartedCorrectCustomDelimiterFormatTest(String input) {
        CustomDelimiterSection section = CustomDelimiterSection.from(input);

        String actual = section.getCustomDelimiterCandidate();

        assertEquals(CORRECT_CANDIDATE.get(), actual);
    }

    @ParameterizedTest
    @MethodSource("provideWrongFormats")
    @DisplayName("입력된 문자열이 커스텀 구분자 형식에 맞지 않는다면 반환하는 커스텀 구분자 후보는 null이 되어야 한다.")
    void wrongCustomDelimiterFormatTest(String input) {
        CustomDelimiterSection section = CustomDelimiterSection.from(input);

        String actual = section.getCustomDelimiterCandidate();

        assertNull(actual);
    }
}
