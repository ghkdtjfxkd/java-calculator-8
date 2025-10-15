package calculator.domain.tokenizing;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperandTest {

    @Test
    @DisplayName("Long_범위를_벗어나는_숫자를_정상적으로_나타내야_한다.")
    void overcomeLongOperand() {
        // 9223372036854775807(Long.MAX_VALUE) + 1
        String overLongRangeNumber  = "9223372036854775808";
        Operand operand = Operand.valueOf(overLongRangeNumber);

        BigInteger expected = new BigInteger(overLongRangeNumber);
        BigInteger actual = operand.getOperand();

        assertEquals(expected, actual);
    }
}
