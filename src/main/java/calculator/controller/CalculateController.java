package calculator.controller;

import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;
import calculator.io.InputView;
import calculator.io.OutputView;
import calculator.service.CalculateService;

public class CalculateController {

    private final CalculateService calculateService;

    private CalculateController(CalculateService calculateService){
        this.calculateService = calculateService;
    }

    public static CalculateController getInstance(CalculateService calculateService){
        return new CalculateController(calculateService);
    }

    public void run() {
        OutputView.print(calculateResult());
        InputView.close();
    }

    private CalculationResponse calculateResult() {
        return calculateService.calculate(request());
    }

    private CalculationRequest request() {
        return InputView.request();
    }
}
