package calculator.domain.tokenizing;

import java.math.BigInteger;

public class Operand extends CalculationElement {

    private final BigInteger operand;

    private Operand(String rawValue) {
        super(rawValue);
        this.operand = new BigInteger(rawValue);
    }

    public static Operand valueOf(String value) {
        return new Operand(value);
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERAND;
    }

    public BigInteger getValue() {
        return operand;
    }

    public BigInteger getOperand() {
        return this.operand;
    }
}
