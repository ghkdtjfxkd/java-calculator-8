package calculator.service;

import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;

@FunctionalInterface
public interface CalculateService {
    CalculationResponse calculate(CalculationRequest request);
}
