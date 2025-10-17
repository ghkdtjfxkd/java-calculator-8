package calculator.domain.tokenizing;

public abstract class CalculationElement {

    protected final String rawValue;

    protected CalculationElement(String rawValue) {
        this.rawValue = rawValue;
    }

    static CalculationElement of(char charSymbol){
        String symbol = String.valueOf(charSymbol);
        return of(symbol);
    }

    static CalculationElement of(String symbol){
        if(isNumeric(symbol)){
            return Operand.valueOf(symbol);
        }
        return Operator.of(symbol);
    }

    protected abstract CalculationElementType getType();

    public boolean isOperand() {
        return getType() == CalculationElementType.OPERAND;
    }

    public boolean isOperator() {
        return getType() == CalculationElementType.OPERATOR;
    }

    private static boolean isNumeric(String value) {
        return value.chars().allMatch(Character::isDigit);
    }

    protected enum CalculationElementType {
        OPERAND, OPERATOR
    }
}
