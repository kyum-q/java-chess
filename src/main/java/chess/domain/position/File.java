package chess.domain.position;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum File {
    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    H('h'),
    ;

    private static final String REGEX = "^[a-h]$";

    private final char value;

    File(char value) {
        this.value = value;
    }

    public static File of(char value) {
        return Arrays.stream(values())
                .filter(file -> file.value == value)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("File 범위는 1-8까지 입니다. : %c".formatted(value)));
    }

    public File move(int amount) {
        return of((char) (value + amount));
    }

    public int calculateFileDifference(File targetFile) {
        return targetFile.value - value;
    }
}
