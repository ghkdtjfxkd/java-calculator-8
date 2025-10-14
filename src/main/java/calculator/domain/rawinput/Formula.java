package calculator.domain.rawinput;

import static calculator.domain.rawinput.Formula.CustomDelimiterBracket.LEFT_BRACKET;
import static calculator.domain.rawinput.Formula.CustomDelimiterBracket.RIGHT_BRACKET;

public class Formula {

    // 커스텀 구분자가 '문자'라는 조건이 제거되면 제거하거나 수정할 것.
    private static final int CUSTOM_DELIMITER_ELEMENT_LENGTH = 1;

    private final String rawInput;

    private Formula(String rawInput) {
        requireNonBlankInput(rawInput);
        this.rawInput = rawInput;
    }

    public static Formula from(String input) {
        return new Formula(input);
    }

    private void requireNonBlankInput(String rawInput) {
        if (rawInput == null || rawInput.isEmpty()) {
            throw new IllegalArgumentException("입력 값이 비어 있습니다.");
        }
    }

    private boolean hasCustomDelimiter(String rawInput) {
        int startIndex = 0;
        int endIndex = customDelimiterSectionSize();

        if(canContainCustomDelimiterSection(rawInput.trim())){
            return false;
        }

        String section = rawInput.trim().substring(startIndex, endIndex);
        return CustomDelimiterBracket.isCovered(section);
    }

    private boolean canContainCustomDelimiterSection(String input) {
        return input.length() < customDelimiterSectionSize();
    }

    private int customDelimiterSectionSize() {
        return LEFT_BRACKET.length() + CUSTOM_DELIMITER_ELEMENT_LENGTH + RIGHT_BRACKET.length();
    }

    protected enum CustomDelimiterBracket {
        LEFT_BRACKET("//"),
        RIGHT_BRACKET("\\n"); // `\` + `n` 합쳐진 문자를 의미함. `\n`은 개행문자를 의미하지 않음.

        private final String symbol;

        CustomDelimiterBracket(String symbol) {
            this.symbol = symbol;
        }

        int length() {
            return symbol.length();
        }

        static boolean isCovered(String section) {
            return section.startsWith(LEFT_BRACKET.symbol) && section.endsWith(RIGHT_BRACKET.symbol);
        }
    }
}
