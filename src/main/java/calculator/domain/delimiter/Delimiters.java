package calculator.domain.delimiter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Delimiters {

    private final Set<String> delimiters;

    private Delimiters() {
        this.delimiters = Set.copyOf(DefaultDelimiter.getDelimiters());
    }

    public static Delimiters defaults() {
        return new Delimiters();
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
