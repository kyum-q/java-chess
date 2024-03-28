package chess.dao.converter;

import chess.domain.position.File;

import java.util.Arrays;

public class FileConverter {
    public static File convertFile(char value) {
        String stringRank = String.valueOf(value);
        return Arrays.stream(File.values())
                .filter(file -> convertChar(file).equals(stringRank))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("File 범위는 1-8까지 입니다. : %c".formatted(value)));
    }

    public static String convertChar(File file) {
        return switch (file) {
            case A -> "a";
            case B -> "b";
            case C -> "c";
            case D -> "d";
            case E -> "e";
            case F -> "f";
            case G -> "g";
            case H -> "h";
        };
    }
}
