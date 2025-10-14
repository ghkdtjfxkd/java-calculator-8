package calculator.domain.delimiter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Delimiters {

    private final Set<String> delimiters;

    private Delimiters() {
        this.delimiters = Set.copyOf(DefaultDelimiter.getDelimiters());
    }

    private Delimiters(String customDelimiter) {
        requireNonNumerical(customDelimiter);
        Set<String> delimiters = new HashSet<>(DefaultDelimiter.getDelimiters());
        delimiters.add(customDelimiter);
        this.delimiters = Set.copyOf(delimiters);
    }

    public static Delimiters defaults() {
        return new Delimiters();
    }

    public Delimiters withCustom(String customDelimiter) {
        return new Delimiters(customDelimiter);
    }

    public boolean has(String delimiter) {
        return this.delimiters.contains(delimiter);
    }

    private void requireNonNumerical(String delimiter) {
        if (isDigitDelimiter(delimiter)) {
            throw new IllegalArgumentException("숫자는 구분자가 될 수 없습니다.");
        }
    }

    private boolean isDigitDelimiter(String delimiter) {
        return delimiter.chars().anyMatch(Character::isDigit);
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
            return Arrays.stream(DefaultDelimiter.values())
                    .map(DefaultDelimiter::getDelimiter)
                    .collect(Collectors.toSet());
        }
    }
}
