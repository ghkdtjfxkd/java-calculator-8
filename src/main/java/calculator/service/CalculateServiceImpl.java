package calculator.service;

import calculator.domain.delimiter.Delimiters;
import calculator.domain.rawinput.Formula;
import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;

public class CalculateServiceImpl implements CalculateService {

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        Formula formula = Formula.from(request.input());

        Delimiters delimiters = defineDelimiters(formula.getCustomDelimiterCandidate());

        return CalculationResponse.from(null);
    }

    private Delimiters defineDelimiters(String customDelimiterCandidate) {
        Delimiters delimiters = Delimiters.defaults();
        if(customDelimiterCandidate != null) {
            delimiters = delimiters.withCustom(customDelimiterCandidate);
        }

        return delimiters;
    }
}
