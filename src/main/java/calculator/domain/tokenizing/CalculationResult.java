package calculator.domain.tokenizing;

import java.math.BigInteger;

public class CalculationResult {

    private BigInteger value;

    private CalculationResult(BigInteger value) {
        this.value = value;
    }

    public CalculationResult after(Operator operator, Operand operand) {
        return new CalculationResult(operateAt(operator, operand));
    }

    private BigInteger operateAt(Operator operator, Operand operand) {
        return operator.operate(this.value, operand.getOperand());
    }

    public BigInteger getValue() {
        return value;
    }
}
