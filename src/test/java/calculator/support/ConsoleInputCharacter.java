package calculator.support;

import java.util.stream.Stream;

public enum ConsoleInputCharacter {

    BACKSLASH("\\", "\\\\"),  // \
    TAB("\t", "\\t"),         // 탭
    DOUBLE_QUOTE("\"", "\\\""),
    SINGLE_QUOTE("\'", "\\\'");

    private final String visible; // 콘솔에서 보이는 실제 문자
    private final String literal; // 자바 리터럴 문자열 표현

    ConsoleInputCharacter(String visible, String literal) {
        this.visible = visible;
        this.literal = literal;
    }

    public String visible() {
        return visible;
    }

    public static Stream<ConsoleInputCharacter> stream() {
        return Stream.of(values());
    }

    public static Stream<String> visibleStream() {
        return stream().map(ConsoleInputCharacter::visible);
    }
}
