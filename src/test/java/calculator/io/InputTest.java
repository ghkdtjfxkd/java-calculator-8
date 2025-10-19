package calculator.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import calculator.dto.CalculationRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        outputStreamCaptor.reset();
        System.setOut(new PrintStream(outputStreamCaptor));
        InputView.close();
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        InputView.close();
    }

    @Test
    @DisplayName("사용자가 입력한 요청과 전달된 값은 같아야한다.")
    void correctInputTest() {
        String provided = "1,2";
        String simulatedInput = provided + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        CalculationRequest request = InputView.request();

        assertEquals(provided, request.input());
    }

    @Test
    @DisplayName("콘솔이 닫힌 뒤에는 추가적인 입력을 받을 수 없다.")
    void afterCloseConsoleTest() {
        String simulatedInput = "1,2" + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        InputView.request();
        InputView.close();

        assertThrowsExactly(NoSuchElementException.class, InputView::request);
    }
}
