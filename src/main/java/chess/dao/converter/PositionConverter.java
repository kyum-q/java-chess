package chess.dao.converter;

import chess.domain.position.Position;

public class PositionConverter {
    public static final int ROW_INDEX = 1;
    public static final int COLUMN_INDEX = 0;

    private PositionConverter() {
    }

    public static Position convertPosition(String value) {
        return Position.of(
                FileConverter.convertFile(value.charAt(COLUMN_INDEX)),
                RankConverter.convertRank(value.charAt(ROW_INDEX)));
    }

    public static String convertString(Position position) {
        return FileConverter.convertChar(position.file())
                + RankConverter.convertChar(position.rank());
    }
}
