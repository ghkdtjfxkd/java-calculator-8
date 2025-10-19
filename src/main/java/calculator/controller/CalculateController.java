package calculator.controller;

import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;
import calculator.io.InputView;
import calculator.io.OutputView;
import calculator.service.CalculateService;

public class CalculateController {

    private final CalculateService calculateService;

    private CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    public static CalculateController of(CalculateService calculateService) {
        return new CalculateController(calculateService);
    }

    public void run() {
        try {
            CalculationRequest request = request();
            CalculationResponse response = calculateService.calculate(request);
            OutputView.print(response);
        } finally {
            InputView.close();
        }
    }

    private CalculationRequest request() {
        return InputView.request();
    }
}
