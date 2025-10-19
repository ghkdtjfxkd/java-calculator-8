package calculator.domain.tokenizing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Delimiters {

    private final Set<String> delimiters;

    private Delimiters() {
        this.delimiters = Set.copyOf(DefaultDelimiter.getDelimiters());
    }

    private Delimiters(Set<String> delimiters) {
        this.delimiters = Set.copyOf(delimiters);
    }

    public static Delimiters defaults() {
        return new Delimiters();
    }

    public static Delimiters withCustom(String customDelimiterCandidate) {
        requireNonNumerical(customDelimiterCandidate);
        return new Delimiters(getDelimitersWith(customDelimiterCandidate));
    }

    private static void requireNonNumerical(String candidate) {
        if (isDigitDelimiter(candidate)) {
            throw new IllegalArgumentException("숫자는 구분자가 될 수 없습니다.");
        }
    }

    private static boolean isDigitDelimiter(String candidate) {
        return candidate.chars().anyMatch(Character::isDigit);
    }

    private static Set<String> getDelimitersWith(String customDelimiterCandidate) {
        Set<String> delimiters = new HashSet<>(Set.copyOf(DefaultDelimiter.getDelimiters()));
        delimiters.add(customDelimiterCandidate);
        return delimiters;
    }

    boolean has(char symbol) {
        return this.delimiters.contains(String.valueOf(symbol));
    }

    private enum DefaultDelimiter {
        COMMA(","),
        COLON(":");

        final String delimiter;

        DefaultDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        String getDelimiter() {
            return this.delimiter;
        }

        static Set<String> getDelimiters() {
            return Arrays.stream(getSymbols())
                    .map(DefaultDelimiter::getDelimiter)
                    .collect(Collectors.toSet());
        }

        private static DefaultDelimiter[] getSymbols() {
            return DefaultDelimiter.values();
        }
    }
}
