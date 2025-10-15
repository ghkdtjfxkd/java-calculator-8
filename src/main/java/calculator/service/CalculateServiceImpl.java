package calculator.service;

import calculator.domain.calculation.Calculation;
import calculator.domain.delimiter.Delimiters;
import calculator.domain.rawinput.Formula;
import calculator.domain.tokenizing.CalculationElement;
import calculator.domain.tokenizing.Tokens;
import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;
import java.math.BigInteger;
import java.util.stream.Stream;

public class CalculateServiceImpl implements CalculateService {

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        Formula formula = Formula.from(request.input());
        Stream<CalculationElement> tokens = tokenize(formula);
        BigInteger result = calculateFrom(tokens);

        return CalculationResponse.from(result.toString());
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

    private BigInteger calculateFrom(Stream<CalculationElement> tokens) {
        Calculation calculation =  Calculation.from(tokens);

        return calculation.calculate(tokens);
    }
}
