package calculator.service;

import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;

public interface CalculateService {
    CalculationResponse calculate(CalculationRequest request);
}
