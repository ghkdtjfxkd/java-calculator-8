package calculator.domain.vo;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OperandTest {

    @Test
    @DisplayName("Long 범위를 벗어나는 숫자를 정상적으로 반환해야한다.")
    void overcomeLongOperandsTest() {
        // 9223372036854775807(Long.MAX_VALUE) + 1
        String overLongRangeNumber  = "9223372036854775808";
        Operand operand = Operand.valueOf(overLongRangeNumber);

        BigInteger expected = new BigInteger(overLongRangeNumber);
        BigInteger actual = operand.getValue();

        assertEquals(expected, actual);
    }
}
