package calculator.domain.rawinput;

import static calculator.domain.rawinput.CustomDelimiterSection.CustomDelimiterBracket.LEFT_BRACKET;
import static calculator.domain.rawinput.CustomDelimiterSection.CustomDelimiterBracket.RIGHT_BRACKET;

public class CustomDelimiterSection {

    // 커스텀 구분자가 '문자'라는 조건이 제거되면 제거하거나 수정할 것.
    private static final int CUSTOM_DELIMITER_ELEMENT_LENGTH = 1;

    private final String section;

    private CustomDelimiterSection(String section) {
        this.section = section;
    }

    public static CustomDelimiterSection from(String rawInput) {
        String section = extractSectionFrom(rawInput);
        return new CustomDelimiterSection(section);
    }

    private static String extractSectionFrom(String rawInput) {
        if(!canContainCustomDelimiterSection(rawInput)) {
            return null;
        }
        int startIndex = 0;
        int endIndex = specifiedCustomDelimiterSectionSize();

        return rawInput.trim().substring(startIndex, endIndex);
    }

    private static boolean canContainCustomDelimiterSection(String rawInput) {
        return rawInput.length() >= specifiedCustomDelimiterSectionSize();
    }

    String getCustomDelimiterCandidate() {
        if(hasCustomDelimiter()) {
            return removeBracketsTo(section);
        }
        return null;
    }

    boolean hasCustomDelimiter() {
        if(section == null) {
            return false;
        }
        return CustomDelimiterBracket.isCovered(section);
    }

    private String removeBracketsTo(String section) {
        int startIndex = LEFT_BRACKET.length();
        int endIndex = specifiedCustomDelimiterSectionSize() - RIGHT_BRACKET.length();

        return section.substring(startIndex, endIndex);
    }

    int specifiedLength() {
        return specifiedCustomDelimiterSectionSize();
    }

    private static int specifiedCustomDelimiterSectionSize() {
        return LEFT_BRACKET.length() + CUSTOM_DELIMITER_ELEMENT_LENGTH + RIGHT_BRACKET.length();
    }

    enum CustomDelimiterBracket {
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
