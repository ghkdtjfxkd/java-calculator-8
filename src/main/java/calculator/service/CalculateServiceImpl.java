package calculator.service;

import calculator.domain.delimiter.Delimiters;
import calculator.domain.rawinput.Formula;
import calculator.domain.tokenizing.CalculationElement;
import calculator.domain.tokenizing.Tokens;
import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;
import java.util.stream.Stream;

public class CalculateServiceImpl implements CalculateService {

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        Formula formula = Formula.from(request.input());
        tokenize(formula);

        return CalculationResponse.from(null);
    }

    private Delimiters defineDelimiters(String customDelimiterCandidate) {
        Delimiters delimiters = Delimiters.defaults();
        if(customDelimiterCandidate != null) {
            delimiters = delimiters.withCustom(customDelimiterCandidate);
        }
        return delimiters;
    }

    private Stream<CalculationElement> tokenize(Formula formula) {
        Delimiters delimiters = defineDelimiters(formula.getCustomDelimiterCandidate());
        String actualFormula = formula.getActualFormula();

        Tokens tokens = Tokens.of(actualFormula, delimiters);
        return tokens.stream();
    }
}
