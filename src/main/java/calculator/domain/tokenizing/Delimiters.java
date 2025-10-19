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

    public static Delimiters withCustom(String customDelimiter) {
        requireNonNumerical(customDelimiter);
        return new Delimiters(getDelimitersWith(customDelimiter));
    }

    private static void requireNonNumerical(String delimiter) {
        if (isDigitDelimiter(delimiter)) {
            throw new IllegalArgumentException("숫자는 구분자가 될 수 없습니다.");
        }
    }

    private static boolean isDigitDelimiter(String delimiter) {
        return delimiter.chars().anyMatch(Character::isDigit);
    }

    private static Set<String> getDelimitersWith(String customDelimiter) {
        Set<String> delimiters = new HashSet<>(Set.copyOf(DefaultDelimiter.getDelimiters()));
        delimiters.add(customDelimiter);
        return delimiters;
    }

    boolean has(char delimiter) {
        return this.delimiters.contains(String.valueOf(delimiter));
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
