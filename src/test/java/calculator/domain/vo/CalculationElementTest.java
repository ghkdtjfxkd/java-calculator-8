package calculator.domain.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.domain.vo.CalculationElement.CalculationElementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculationElementTest {

    @Test
    @DisplayName("계산 요소가 숫자로만 이루어져 있다면 피연산자(Operand)여야 한다.")
    void onlyNumericInputCreateOperandTest() {
        String onlyNumber = "111111";
        CalculationElement calculationElement = CalculationElement.of(onlyNumber);

        CalculationElementType expected = CalculationElementType.OPERAND;
        CalculationElementType actual = calculationElement.getType();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("계산 요소가 숫자가 아니라면 연산자(Operator)여야 한다.")
    void nonNumericInputCreateOperatorTest() {
        String nonNumericInput = "-";
        CalculationElement calculationElement = CalculationElement.of(nonNumericInput);

        CalculationElementType expected = CalculationElementType.OPERATOR;
        CalculationElementType actual = calculationElement.getType();

        assertEquals(expected, actual);
    }
}
