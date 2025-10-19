package calculator.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.dto.CalculationResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class OutputViewTest {

    private static final String CORRECT_RESULT_FORMAT = "결과 : %s";

    @ParameterizedTest
    @MethodSource("provideResponses")
    @DisplayName("계산 결과 응답이 정상적인 포맷(결과 : <문자열>)으로 나와야 한다.")
    void printFormatsResultProperly(CalculationResponse calculationResponse) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        try {
            OutputView.print(calculationResponse);

            String expected = String.format(CORRECT_RESULT_FORMAT, calculationResponse.output())
                    + System.lineSeparator();
            String actual = outputStream.toString();

            assertEquals(expected, actual);
        } finally {
            System.setOut(printStream);
        }
    }

    private static Stream<CalculationResponse> provideResponses() {
        return Stream.of(
                CalculationResponse.from("0"),
                CalculationResponse.from("1"),
                CalculationResponse.from("9223372036854775808"));
    }
}
