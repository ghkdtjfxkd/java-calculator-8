package calculator.domain.calculation;

import calculator.domain.vo.CalculationElement;
import calculator.domain.vo.Operand;
import calculator.domain.vo.Operator;
import java.util.stream.Stream;

class TestcaseMethods {

    static Stream<Operand> provideOnlyOneOperandTokens() {
        return Stream.of(
                Operand.valueOf("1"),
                Operand.valueOf("123456"),
                Operand.valueOf("9223372036854775808") // 9223372036854775807(Long.MAX_VALUE) + 1
        );
    }

    static Stream<Stream<CalculationElement>> provideCorrectSequences() {
        return Stream.of(
                correctSequenceBasic(),
                correctSequenceMixedOperatorValue(),
                correctSequenceContainOvercomeLongValueOperand()
        );
    }

    static Stream<Stream<CalculationElement>> provideWrongSequences() {
        return Stream.of(
                wrongSequenceTokensOnlyOperator(),
                wrongSequenceTokensStartAtOperator(),
                wrongSequenceTokensEndAtOperator(),
                wrongSequenceTokensOperatorNextTokenIsOperator()
        );
    }

    private static Stream<CalculationElement> correctSequenceBasic() {
        return Stream.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("2")
        );
    }

    private static Stream<CalculationElement> correctSequenceMixedOperatorValue() {
        return Stream.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("12"),
                Operator.of(":"),
                Operand.of("123")
        );
    }

    private static Stream<CalculationElement> correctSequenceContainOvercomeLongValueOperand() {
        return Stream.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("9223372036854775808") // 9223372036854775807(Long.MAX_VALUE) + 1
        );
    }

    private static Stream<CalculationElement> wrongSequenceTokensOperatorNextTokenIsOperator() {
        return Stream.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operator.of(","),
                Operand.valueOf("2")
        );
    }

    private static Stream<CalculationElement> wrongSequenceTokensEndAtOperator() {
        return Stream.of(
                Operand.valueOf("1"),
                Operator.of(","),
                Operand.valueOf("2"),
                Operator.of(",")
        );
    }

    private static Stream<CalculationElement> wrongSequenceTokensStartAtOperator() {
        return Stream.of(
                Operator.of(","),
                Operand.valueOf("2")
        );
    }

    private static Stream<CalculationElement> wrongSequenceTokensOnlyOperator() {
        return Stream.of(
                Operator.of(",")
        );
    }
}
