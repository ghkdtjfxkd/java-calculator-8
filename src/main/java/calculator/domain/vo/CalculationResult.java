package calculator.domain.vo;

import java.math.BigInteger;

public class CalculationResult {

    private final BigInteger value;

    private CalculationResult(BigInteger value) {
        this.value = value;
    }

    public static CalculationResult of(BigInteger value) {
        requireNonNull(value);
        return new CalculationResult(value);
    }

    private static void requireNonNull(BigInteger value) {
        if(value == null) {
            throw new IllegalArgumentException("계산 결과는 null이 올 수 없습니다.");
        }
    }

    public CalculationResult plus(BigInteger addend) {
        if(addend == null || addend.equals(BigInteger.ZERO)) {
            return this;
        }
        return new CalculationResult(this.value.add(addend));
    }

    public BigInteger getValue() {
        return this.value;
    }
}
