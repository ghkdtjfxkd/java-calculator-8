package calculator.domain.rawinput;

import static calculator.domain.rawinput.TestElements.CORRECT_CANDIDATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CustomDelimiterSectionTest {

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

    @ParameterizedTest(name = "[{index}] 이스케이프 문자 테스트: {0}")
    @MethodSource("provideWrappedConsoleInputCharactersByCorrectFormats")
    @DisplayName("이스케이프 문자도 커스텀 구분자로 정상 인식되어야 한다")
    void shouldRecognizeEscapeCharactersAsDelimiter(String wrapped) {
        CustomDelimiterSection section = CustomDelimiterSection.from(wrapped);

        String expected = expectedCandidateFromWrapped(wrapped);
        String actual = section.getCustomDelimiterCandidate();

        assertEquals(expected, actual);
    }

    private static String expectedCandidateFromWrapped(String wrapped) {
        String left = TestElements.CORRECT_LEFT.get();   // "//"
        String right = TestElements.CORRECT_RIGHT.get();  // "\" + "n", 리터럴 "\\n"
        int start = left.length();
        int end = wrapped.length() - right.length();
        return wrapped.substring(start, end);
    }

    private static Stream<String> provideWrappedConsoleInputCharactersByCorrectFormats() {
        return TestcaseMethods.provideWrappedConsoleInputCharactersByCorrectFormats();
    }

    private static Stream<String> provideCorrectFormats() {
        return TestcaseMethods.provideCorrectFormats();
    }

    private static Stream<String> provideWrongFormats() {
        return TestcaseMethods.provideWrongFormats();
    }
}
