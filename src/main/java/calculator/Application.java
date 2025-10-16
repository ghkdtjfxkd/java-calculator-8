package calculator;

import calculator.controller.CalculateController;
import calculator.service.CalculateService;
import calculator.service.CalculateServiceImpl;

public class Application {
    public static void main(String[] args) {
        initController().run();
    }

    private static CalculateController initController() {
        CalculateService calculateService = new CalculateServiceImpl();
        return CalculateController.of(calculateService);
    }
}
