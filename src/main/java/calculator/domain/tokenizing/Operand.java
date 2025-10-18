package calculator.domain.tokenizing;

import java.math.BigInteger;

public class Operand extends CalculationElement {

    private final BigInteger operand;

    private Operand(String rawValue) {
        super(rawValue);
        this.operand = new BigInteger(rawValue);
    }

    static Operand valueOf(String value) {
        return new Operand(value);
    }

    @Override
    protected CalculationElementType getType() {
        return CalculationElementType.OPERAND;
    }

    public BigInteger getValue() {
        return operand;
    }
}
