package calculator.domain.tokenizing;

public abstract class CalculationElement {

    protected final String rawValue;

    protected CalculationElement(String rawValue) {
        this.rawValue = rawValue;
    }

    public static CalculationElement of(String symbol){
        if(isNumeric(symbol)){
            return Operand.valueOf(symbol);
        }
        return Operator.of(symbol);
    }

    public static CalculationElement of(char charSymbol){
        String symbol = String.valueOf(charSymbol);
        return of(symbol);
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
