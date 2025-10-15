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

    public abstract ElementType getType();

    public boolean isOperand() {
        return getType() == ElementType.OPERAND;
    }

    public boolean isOperator() {
        return getType() == ElementType.OPERATOR;
    }

    private static boolean isNumeric(String value) {
        return value.chars().allMatch(Character::isDigit);
    }

    public enum ElementType {
        OPERAND, OPERATOR
    }
}
