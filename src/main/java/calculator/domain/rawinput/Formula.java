package calculator.domain.rawinput;

import java.util.Optional;

public class Formula {

    private final String rawInput;
    private final CustomDelimiterSection customDelimiterSection;

    private Formula(String rawInput) {
        this.rawInput = rawInput;
        this.customDelimiterSection = extractCustomDelimiterSection(rawInput);
    }

    public static Formula from(String rawInput) {
        requireNonNullInput(rawInput);
        return new Formula(rawInput);
    }

    private static void requireNonNullInput(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("입력 값이 null 입니다.");
        }
    }

    public String getActualFormula() {
        if (rawInputContainCustomDelimiter()) {
            return extractActualFormulaSection();
        }
        return rawInput;
    }

    public Optional<String> getCustomDelimiterCandidate() {
        return Optional.ofNullable(this.customDelimiterSection.getCustomDelimiterCandidate());
    }

    private CustomDelimiterSection extractCustomDelimiterSection(String rawInput) {
        return CustomDelimiterSection.from(rawInput);
    }

    private boolean rawInputContainCustomDelimiter() {
        return this.customDelimiterSection.hasCustomDelimiter();
    }

    private String extractActualFormulaSection() {
        int startIndex = customDelimiterSectionSize();
        int endIndex = rawInput.length();

        return rawInput.substring(startIndex, endIndex);
    }

    private int customDelimiterSectionSize() {
        return customDelimiterSection.specifiedLength();
    }
}
