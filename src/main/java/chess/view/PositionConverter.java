package chess.view;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;

import java.util.regex.Pattern;

public class PositionConverter {
    public static final int ROW_INDEX = 1;
    public static final int COLUMN_INDEX = 0;

    private PositionConverter() {
    }

    public static Position generate(String value) {
        return Position.of(File.of(value.charAt(COLUMN_INDEX)), Rank.of(value.charAt(ROW_INDEX)));
    }
}
