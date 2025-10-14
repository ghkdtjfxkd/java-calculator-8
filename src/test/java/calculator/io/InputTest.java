package calculator.io;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("사용자가_입력한_문자열과_입력받은_값은_같아야_한다.")
    void correctInput() {
        String provided = "1,2";
        String simulatedInput = provided + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String actual = InputView.read();

        assertEquals(provided, actual);
    }

    @Test
    @DisplayName("콘솔이_닫힌_뒤에는_추가적인_입력을_받을_수_없다.")
    void afterCloseConsole() {
        String simulatedInput = "1,2" + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        InputView.read();
        InputView.close();

        assertThrowsExactly(NoSuchElementException.class, InputView::read);
    }
}
