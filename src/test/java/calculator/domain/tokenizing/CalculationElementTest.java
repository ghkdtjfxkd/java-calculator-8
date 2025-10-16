package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.*;

import calculator.domain.tokenizing.CalculationElement.ElementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculationElementTest {

    @Test
    @DisplayName("계산_요소가_숫자로만_이루어져_있다면_피연산자(Operand)여야_한다.")
    void operand() {
        String onlyNumber = "111111";
        CalculationElement calculationElement = CalculationElement.from(onlyNumber);

        ElementType expected = ElementType.OPERAND;
        ElementType actual = calculationElement.getType();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("계산_요소가_숫자가_아니라면_연산자(Operator)여야_한다.")
    void operator() {
        String onlyNumber = "-";
        CalculationElement calculationElement = CalculationElement.from(onlyNumber);

        ElementType expected = ElementType.OPERATOR;
        ElementType actual = calculationElement.getType();

        assertEquals(expected, actual);
    }
}
