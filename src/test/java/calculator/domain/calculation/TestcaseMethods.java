package calculator.domain.calculation;

import calculator.domain.vo.CalculationElement;
import calculator.domain.vo.Operand;
import calculator.domain.vo.Operator;
import java.util.List;
import java.util.stream.Stream;

class TestcaseMethods {

    static Stream<Operand> provideOnlyOneOperandTokens() {
        return Stream.of(
                Operand.valueOf("1"),
                Operand.valueOf("123456"),
                Operand.valueOf("9223372036854775808") // 9223372036854775807(Long.MAX_VALUE) + 1
        );
    }

    static Stream<List<CalculationElement>> provideCorrectSequences() {
        return Stream.of(
                correctSequenceBasic(),
                correctSequenceMixedOperatorValue(),
                correctSequenceContainOvercomeLongValueOperand()
        );
    }

    static Stream<List<CalculationElement>> provideWrongSequences() {
        return Stream.of(
                wrongSequenceTokensOnlyOperator(),
                wrongSequenceTokensStartAtOperator(),
                wrongSequenceTokensEndAtOperator(),
                wrongSequenceTokensOperatorNextTokenIsOperator()
        );
    }

    private static List<CalculationElement> correctSequenceBasic() {
        return List.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("2")
        );
    }

    private static List<CalculationElement> correctSequenceMixedOperatorValue() {
        return List.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("12"),
                Operator.of(":"),
                Operand.of("123")
        );
    }

    private static List<CalculationElement> correctSequenceContainOvercomeLongValueOperand() {
        return List.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("9223372036854775808") // 9223372036854775807(Long.MAX_VALUE) + 1
        );
    }

    private static List<CalculationElement> wrongSequenceTokensOperatorNextTokenIsOperator() {
        return List.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operator.of(","),
                Operand.valueOf("2")
        );
    }

    private static List<CalculationElement> wrongSequenceTokensEndAtOperator() {
        return List.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("2"),
                Operator.of(",")
        );
    }

    private static List<CalculationElement> wrongSequenceTokensStartAtOperator() {
        return List.of(
                Operator.of(","),
                Operand.valueOf("2")
        );
    }

    private static List<CalculationElement> wrongSequenceTokensOnlyOperator() {
        return List.of(
                Operator.of(",")
        );
    }
}
