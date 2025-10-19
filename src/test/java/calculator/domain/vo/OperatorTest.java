package calculator.domain.vo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperatorTest {

    @Test
    @DisplayName("연산자는 숫자를 포함해서는 안된다.")
    void numericOperatorTest() {
        String operatorCandidate = "2";
        assertThrows(IllegalArgumentException.class, () -> Operator.of(operatorCandidate));
    }
}
