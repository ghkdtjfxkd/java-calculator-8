package calculator.domain.calculation;

import java.math.BigInteger;

public class CalculationResult {

    private final BigInteger value;

    private CalculationResult(BigInteger value) {
        this.value = value;
    }

    public static CalculationResult of(BigInteger value) {
        return new CalculationResult(value);
    }

    public CalculationResult plus(BigInteger addend) {
        return new CalculationResult(this.value.add(addend));
    }

    public BigInteger getValue() {
        return this.value;
    }
}
