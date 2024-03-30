package chess.dao.converter;

import chess.domain.piece.*;
import chess.domain.piece.character.Kind;
import chess.domain.piece.character.Team;

public class PieceConverter {
    public static String converterId(Piece piece) {
        return String.valueOf(piece.findKind().ordinal())
                + piece.team().ordinal()
                + booleanToTinyInt(piece.isMoved());
    }

    public static String booleanToTinyInt(boolean value) {
        if(value) {
            return "1";
        }
        return "0";
    }

    public static Piece converterPiece(String kind, Team team, boolean isMoved) {
        return switch (Kind.valueOf(kind)) {
            case PAWN -> new Pawn(team, isMoved);
            case KNIGHT -> new Knight(team, isMoved);
            case BISHOP -> new Bishop(team, isMoved);
            case ROOK -> new Rook(team, isMoved);
            case QUEEN -> new Queen(team, isMoved);
            case KING -> new King(team, isMoved);
        };
    }
}
