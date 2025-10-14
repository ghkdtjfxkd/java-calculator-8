package calculator.io;

import calculator.dto.CalculationRequest;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String INPUT_GUIDE_MESSAGE = "덧셈할 문자열을 입력해 주세요.";

    public static CalculationRequest request() {
        String userInput = InputView.read();
        return CalculationRequest.of(userInput);
    }

    private static String read() {
        announceGuideMessage();
        return Console.readLine();
    }

    private static void announceGuideMessage() {
        System.out.println(INPUT_GUIDE_MESSAGE);
    }

    public static void close() {
        Console.close();
    }
}
