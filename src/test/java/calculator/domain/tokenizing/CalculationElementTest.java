package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.tokenizing.CalculationElement.CalculationElementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculationElementTest {

    @Test
    @DisplayName("계산_요소가_숫자로만_이루어져_있다면_피연산자(Operand)여야_한다.")
    void operand() {
        String onlyNumber = "111111";
        CalculationElement calculationElement = CalculationElement.of(onlyNumber);

        CalculationElementType expected = CalculationElementType.OPERAND;
        CalculationElementType actual = calculationElement.getType();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("계산_요소가_숫자가_아니라면_연산자(Operator)여야_한다.")
    void operator() {
        String onlyNumber = "-";
        CalculationElement calculationElement = CalculationElement.of(onlyNumber);

        CalculationElementType expected = CalculationElementType.OPERATOR;
        CalculationElementType actual = calculationElement.getType();

        assertEquals(expected, actual);
    }
}
