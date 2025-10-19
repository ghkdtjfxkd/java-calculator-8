package calculator.domain.rawinput;

enum TestElements {
    CORRECT_LEFT("//"),
    CORRECT_RIGHT("\\n"),
    CORRECT_CANDIDATE("."),

    WRONG_LEFT("/"),
    WRONG_RIGHT("\\t");

    private final String value;

    TestElements(String value) {
        this.value = value;
    }

    static String getCorrectFormat() {
        return CORRECT_LEFT.value + CORRECT_CANDIDATE.value + CORRECT_RIGHT.value;
    }

    String get() {
        return value;
    }
}
