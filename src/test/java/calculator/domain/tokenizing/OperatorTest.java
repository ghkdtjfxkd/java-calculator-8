package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperatorTest {

    @Test
    @DisplayName("연산자는_숫자를_포함해서는_안된다.")
    void operate() {
        String operatorCandidate  = ",2";

        assertThrows(IllegalArgumentException.class,() -> Operator.of(operatorCandidate));
    }
}
