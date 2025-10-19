package calculator.domain.rawinput;

import java.util.Optional;

public class Formula {

    private final String rawInput;
    private final CustomDelimiterSection customDelimiterSection;

    private Formula(String rawInput, CustomDelimiterSection customDelimiterSection) {
        this.rawInput = rawInput;
        this.customDelimiterSection = customDelimiterSection;
    }

    public static Formula from(String rawInput) {
        requireNonNullInput(rawInput);
        return new Formula(rawInput, CustomDelimiterSection.from(rawInput));
    }

    private static void requireNonNullInput(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("입력 값이 null 입니다.");
        }
    }

    public String getActualFormula() {
        if (customDelimiterSection.hasCustomDelimiterCandidate()) {
            return extractActualFormulaSection();
        }
        return rawInput;
    }

    public Optional<String> getCustomDelimiterCandidate() {
        return Optional.ofNullable(customDelimiterSection.getCustomDelimiterCandidate());
    }

    private String extractActualFormulaSection() {
        int startIndex = customDelimiterSection.specifiedLength();
        int endIndex = rawInput.length();

        return rawInput.substring(startIndex, endIndex);
    }
}
