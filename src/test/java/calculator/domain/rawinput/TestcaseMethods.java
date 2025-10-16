package calculator.domain.rawinput;

import static calculator.domain.rawinput.TestElements.CORRECT_CANDIDATE;
import static calculator.domain.rawinput.TestElements.CORRECT_LEFT;
import static calculator.domain.rawinput.TestElements.CORRECT_RIGHT;
import static calculator.domain.rawinput.TestElements.WRONG_LEFT;
import static calculator.domain.rawinput.TestElements.WRONG_RIGHT;
import static calculator.domain.rawinput.TestElements.getCorrectFormat;

import java.util.stream.Stream;

class TestcaseMethods {

    static Stream<String> provideCorrectFormats() {
        String correctFormat = TestElements.getCorrectFormat();
        return Stream.of(
                correctFormat + "1,2",
                correctFormat + "1,2,3",
                correctFormat +  " ",
                correctFormat + ",,",
                correctFormat
        );
    }

    static Stream<String> provideWrongFormats() {
        String correctFormat = getCorrectFormat();

        return Stream.of(
                "1,2,3" + correctFormat,
                "1" + correctFormat + "2",
                WRONG_LEFT.get() + CORRECT_CANDIDATE.get() + CORRECT_RIGHT.get() + "1,2,3",
                CORRECT_LEFT.get() + CORRECT_CANDIDATE.get() + WRONG_RIGHT.get() + "1,2,3"
        );
    }
}
