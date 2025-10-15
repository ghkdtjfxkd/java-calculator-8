package calculator.io;

import calculator.dto.CalculationResponse;

public class OutputView {

    private static final String RESULT_FORMAT = "결과 : ";

    public static void print(CalculationResponse result) {
        String resultFormatted = String.format(RESULT_FORMAT, result.output());
        System.out.println(resultFormatted);
    }
}
