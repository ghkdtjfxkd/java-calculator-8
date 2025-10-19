package calculator.domain.rawinput;

public class CustomDelimiterSection {

    private final String section;

    private CustomDelimiterSection(String section) {
        this.section = section;
    }

    static CustomDelimiterSection from(String rawInput) {
        String section = extractSectionFrom(rawInput);
        return new CustomDelimiterSection(section);
    }

    private static String extractSectionFrom(String rawInput) {
        if (!canContainCustomDelimiterSection(rawInput)) {
            return null;
        }
        int startIndex = 0;
        int endIndex = specifiedCustomDelimiterSectionSize();

        return rawInput.trim().substring(startIndex, endIndex);
    }

    private static boolean canContainCustomDelimiterSection(String rawInput) {
        return rawInput.length() >= specifiedCustomDelimiterSectionSize();
    }

    private static int specifiedCustomDelimiterSectionSize() {
        return CustomDelimiterSyntax.LEFT_BRACKET.length()
                + CustomDelimiterSyntax.customDelimiterCandidateLength()
                + CustomDelimiterSyntax.RIGHT_BRACKET.length();
    }

    String getCustomDelimiterCandidate() {
        if (hasCustomDelimiterCandidate()) {
            return removeBracketsTo(section);
        }
        return null;
    }

    boolean hasCustomDelimiterCandidate() {
        if (section == null) {
            return false;
        }
        return CustomDelimiterSyntax.isWrapped(section);
    }

    int specifiedLength() {
        return specifiedCustomDelimiterSectionSize();
    }

    private String removeBracketsTo(String section) {
        int startIndex = CustomDelimiterSyntax.LEFT_BRACKET.length();
        int endIndex = specifiedCustomDelimiterSectionSize() - CustomDelimiterSyntax.RIGHT_BRACKET.length();

        return section.substring(startIndex, endIndex);
    }

    private enum CustomDelimiterSyntax {
        LEFT_BRACKET("//"),
        RIGHT_BRACKET("\\n"); // `\` + `n` 합쳐진 문자를 의미함. `\n`은 개행문자를 의미하지 않음.

        // 커스텀 구분자가 '문자'라는 조건이 제거되면, 수정할 것.
        private static final int CUSTOM_DELIMITER_ELEMENT_LENGTH = 1;

        private final String symbol;

        CustomDelimiterSyntax(String symbol) {
            this.symbol = symbol;
        }

        static int customDelimiterCandidateLength() {
            return CUSTOM_DELIMITER_ELEMENT_LENGTH;
        }

        static boolean isWrapped(String section) {
            return section.startsWith(LEFT_BRACKET.symbol) && section.endsWith(RIGHT_BRACKET.symbol);
        }

        int length() {
            return symbol.length();
        }
    }
}
