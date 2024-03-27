package chess.domain;

import chess.domain.piece.*;
import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;

import java.util.HashMap;
import java.util.Map;

public class BoardFactory {
    private BoardFactory() {
    }

    public static Map<Position, Piece> generateStartBoard() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(createPawnRow(Rank.TWO, Team.WHITE));
        pieces.putAll(createPawnRow(Rank.SEVEN, Team.BLACK));
        pieces.putAll(createEdgeRow(Rank.ONE, Team.WHITE));
        pieces.putAll(createEdgeRow(Rank.EIGHT, Team.BLACK));
        return pieces;
    }

    private static Map<Position, Piece> createPawnRow(Rank rank, Team team) {
        Map<Position, Piece> pawnRow = new HashMap<>();
        for (File file : File.values()) {
            pawnRow.put(Position.of(file, rank), new Pawn(team));
        }
        return pawnRow;
    }

    private static Map<Position, Piece> createEdgeRow(Rank rank, Team team) {
        return new HashMap<>(Map.of(
                Position.of(File.A, rank), new Rook(team),
                Position.of(File.B, rank), new Knight(team),
                Position.of(File.C, rank), new Bishop(team),
                Position.of(File.D, rank), new Queen(team),
                Position.of(File.E, rank), new King(team),
                Position.of(File.F, rank), new Bishop(team),
                Position.of(File.G, rank), new Knight(team),
                Position.of(File.H, rank), new Rook(team)
        ));
    }
}
