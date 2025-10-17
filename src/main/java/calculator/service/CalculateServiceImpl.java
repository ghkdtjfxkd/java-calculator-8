package calculator.service;

import calculator.domain.calculation.Calculation;
import calculator.domain.tokenizing.Delimiters;
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
        Stream<CalculationElement> tokens = tokenized(formula);
        BigInteger result = calculate(tokens);

        return CalculationResponse.from(result.toString());
    }

    private Stream<CalculationElement> tokenized(Formula formula) {
        Delimiters delimiters = defineDelimitersWith(formula);
        String actualFormula = formula.getActualFormula();

        Tokens tokens = Tokens.from(actualFormula, delimiters);
        return tokens.getTokensStream();
    }

    private Delimiters defineDelimitersWith(Formula formula) {
        return formula.getCustomDelimiterCandidate()
                .map(Delimiters::withCustom)
                .orElseGet(Delimiters::defaults);
    }

    private BigInteger calculate(Stream<CalculationElement> tokens) {
        Calculation calculation =  Calculation.from(tokens);
        return calculation.calculate();
    }
}
