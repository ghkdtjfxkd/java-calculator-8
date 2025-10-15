package calculator;

import calculator.controller.CalculateController;
import calculator.service.CalculateService;
import calculator.service.CalculateServiceImpl;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        CalculateService calculateService = new CalculateServiceImpl();
        CalculateController controller = CalculateController.getInstance(calculateService);
        controller.run();
    }
}
