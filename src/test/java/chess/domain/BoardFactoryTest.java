package chess.domain;

import chess.domain.piece.*;
import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BoardFactoryTest {
    @DisplayName("Board에서 위치와 Character를 알 수 있다.")
    @Test
    void mapPositionToCharacter() {
        Map<Position, Piece> expected = Map.ofEntries(
                Map.entry(Position.of(File.A, Rank.ONE), new Rook(Team.WHITE)),
                Map.entry(Position.of(File.B, Rank.ONE), new Knight(Team.WHITE)),
                Map.entry(Position.of(File.C, Rank.ONE), new Bishop(Team.WHITE)),
                Map.entry(Position.of(File.D, Rank.ONE), new Queen(Team.WHITE)),
                Map.entry(Position.of(File.E, Rank.ONE), new King(Team.WHITE)),
                Map.entry(Position.of(File.F, Rank.ONE), new Bishop(Team.WHITE)),
                Map.entry(Position.of(File.G, Rank.ONE), new Knight(Team.WHITE)),
                Map.entry(Position.of(File.H, Rank.ONE), new Rook(Team.WHITE)),

                Map.entry(Position.of(File.A, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.B, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.C, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.D, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.E, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.F, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.G, Rank.TWO), new Pawn(Team.WHITE)),
                Map.entry(Position.of(File.H, Rank.TWO), new Pawn(Team.WHITE)),

                Map.entry(Position.of(File.A, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.B, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.C, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.D, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.E, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.F, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.G, Rank.SEVEN), new Pawn(Team.BLACK)),
                Map.entry(Position.of(File.H, Rank.SEVEN), new Pawn(Team.BLACK)),

                Map.entry(Position.of(File.A, Rank.EIGHT), new Rook(Team.BLACK)),
                Map.entry(Position.of(File.B, Rank.EIGHT), new Knight(Team.BLACK)),
                Map.entry(Position.of(File.C, Rank.EIGHT), new Bishop(Team.BLACK)),
                Map.entry(Position.of(File.D, Rank.EIGHT), new Queen(Team.BLACK)),
                Map.entry(Position.of(File.E, Rank.EIGHT), new King(Team.BLACK)),
                Map.entry(Position.of(File.F, Rank.EIGHT), new Bishop(Team.BLACK)),
                Map.entry(Position.of(File.G, Rank.EIGHT), new Knight(Team.BLACK)),
                Map.entry(Position.of(File.H, Rank.EIGHT), new Rook(Team.BLACK))
        );

        assertThat(BoardFactory.generateStartBoard()).isEqualTo(expected);
    }
}
