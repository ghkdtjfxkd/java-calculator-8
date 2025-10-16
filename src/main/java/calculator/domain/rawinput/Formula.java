package calculator.domain.rawinput;

import java.util.Optional;

public class Formula {

    private final String rawInput;
    private final CustomDelimiterSection customDelimiterSection;

    private Formula(String rawInput) {
        requireNonNullInput(rawInput);
        this.rawInput = rawInput;
        this.customDelimiterSection = extractCustomDelimiterSection(rawInput);
    }

    private CustomDelimiterSection extractCustomDelimiterSection(String rawInput) {
        return CustomDelimiterSection.from(rawInput);
    }

    public static Formula from(String rawInput) {
        return new Formula(rawInput);
    }

    private void requireNonNullInput(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("입력 값이 Null 입니다.");
        }
    }

    public String getActualFormula() {
        if(customDelimiterSection.hasCustomDelimiter()) {
            return extractActualFormulaSection();
        }
        return rawInput;
    }

    public Optional<String> getCustomDelimiterCandidate() {
        return Optional.ofNullable(customDelimiterSection.getCustomDelimiterCandidate());
    }

    private String extractActualFormulaSection() {
        int startIndex = customDelimiterSectionSize();
        int endIndex = rawInput.length();

        return rawInput.substring(startIndex, endIndex);
    }

    private int customDelimiterSectionSize() {
        return CustomDelimiterSection.size();
    }
}
