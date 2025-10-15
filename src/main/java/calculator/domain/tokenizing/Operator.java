package calculator.domain.tokenizing;

import java.math.BigInteger;
import java.util.function.BinaryOperator;

public class Operator extends CalculationElement {

    private final Operation operation;

    private Operator(String value) {
        super(value);
        this.operation = Operation.PLUS;
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERATOR;
    }

    public static Operator of(String value) {
        requireNonNumeric(value);
        return new Operator(value);
    }

    private static void requireNonNumeric(String value) {
        if(isContainNumericSource(value)) {
            throw new IllegalArgumentException("연산자는 숫자가 올 수 없습니다.");
        }
    }

    private static boolean isContainNumericSource(String value) {
        return value.chars().anyMatch(Character::isDigit);
    }

    public BigInteger operate(BigInteger left, BigInteger right) {
        return operation.operate(left, right);
    }

    private enum Operation {
        PLUS(BigInteger::add);

        private final BinaryOperator<BigInteger> op;

        Operation(BinaryOperator<BigInteger> op) {
            this.op = op;
        }

        public BigInteger operate(BigInteger left, BigInteger right) {
            return op.apply(left, right);
        }
    }
}
