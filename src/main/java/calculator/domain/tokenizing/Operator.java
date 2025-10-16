package calculator.domain.tokenizing;

public class Operator extends CalculationElement {

    private Operator(String value) {
        super(value);
    }

    public static Operator of(String value) {
        requireNonNumeric(value);
        return new Operator(value);
    }

    @Override
    public ElementType getType() {
        return ElementType.OPERATOR;
    }

    private static void requireNonNumeric(String value) {
        if(isContainNumericSource(value)) {
            throw new IllegalArgumentException("연산자는 숫자가 올 수 없습니다.");
        }
    }

    private static boolean isContainNumericSource(String value) {
        return value.chars().anyMatch(Character::isDigit);
    }
}
