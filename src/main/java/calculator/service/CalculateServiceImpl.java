package calculator.service;

import calculator.domain.calculation.Calculation;
import calculator.domain.rawinput.Formula;
import calculator.domain.tokenizing.Delimiters;
import calculator.domain.tokenizing.Tokens;
import calculator.domain.vo.CalculationElement;
import calculator.domain.vo.CalculationResult;
import calculator.dto.CalculationRequest;
import calculator.dto.CalculationResponse;
import java.math.BigInteger;
import java.util.stream.Stream;

public class CalculateServiceImpl implements CalculateService {

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        Formula formula = Formula.from(request.input());
        Stream<CalculationElement> tokens = tokensOf(formula);
        BigInteger result = evaluate(tokens);

        return CalculationResponse.from(result.toString());
    }

    private Stream<CalculationElement> tokensOf(Formula formula) {
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

    private BigInteger evaluate(Stream<CalculationElement> tokens) {
        Calculation calculation = Calculation.from(tokens);
        CalculationResult result = calculation.calculate();

        return result.getValue();
    }
}
