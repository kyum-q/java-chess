package chess.domain;

import chess.domain.piece.character.Team;

public enum Outcome {
    BLACK_WIN,
    WHITE_WIN,
    DRAW,
    ;

    public static Outcome of(Team team) {
        return switch (team) {
            case BLACK -> BLACK_WIN;
            case WHITE -> WHITE_WIN;
        };
    }
}
