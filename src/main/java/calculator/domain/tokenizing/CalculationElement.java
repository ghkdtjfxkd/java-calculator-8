package calculator.domain.tokenizing;

public abstract class CalculationElement {
    protected final String rawValue;

    protected CalculationElement(String rawValue) {
        this.rawValue = rawValue;
    }

    public static CalculationElement from(String value){
        if(isNumeric(value)){
            return Operand.valueOf(value);
        }
        return Operator.of(value);
    }

    protected abstract ElementType getType();

    private static boolean isNumeric(String value) {
        return value.chars().allMatch(Character::isDigit);
    }

    protected enum ElementType {
        OPERAND, OPERATOR
    }
}
