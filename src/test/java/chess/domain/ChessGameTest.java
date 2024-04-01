package chess.domain;

import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Queen;
import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ChessGameTest {

    @DisplayName("체크 메이트 상태일 시, 체크 메이트한 팀이 승이다.")
    @Test
    void findOutcomeCheckMateWin() {
        Board board = new Board(Map.of(
                Position.of(File.H, Rank.ONE), new King(Team.WHITE),
                Position.of(File.H, Rank.TWO), new Pawn(Team.WHITE),
                Position.of(File.F, Rank.ONE), new Queen(Team.BLACK),
                Position.of(File.A, Rank.ONE), new King(Team.BLACK)
        ));

        assertThat(new ChessGame(board).findOutcome()).isEqualTo(Outcome.BLACK_WIN);
    }

    @DisplayName("체크상태가 아닐 시, 기물의 점수의 합이 동일 할 시 무승부이다.")
    @Test
    void findOutcomeDraw() {
        Board board = new Board(Map.of(
                Position.of(File.A, Rank.ONE), new King(Team.WHITE),
                Position.of(File.B, Rank.TWO), new Pawn(Team.WHITE),
                Position.of(File.A, Rank.SEVEN), new King(Team.BLACK),
                Position.of(File.B, Rank.EIGHT), new Pawn(Team.BLACK)
        ));

        assertThat(new ChessGame(board).findOutcome()).isEqualTo(Outcome.DRAW);
    }

    @DisplayName("체크상태가 아닐 시, 기물의 점수의 합이 흰색 팀이 클 경우 흰색 팀의 승이다.")
    @Test
    void findOutcomeWhiteWin() {
        Board board = new Board(Map.of(
                Position.of(File.A, Rank.ONE), new King(Team.WHITE),
                Position.of(File.B, Rank.TWO), new Pawn(Team.WHITE),
                Position.of(File.A, Rank.SEVEN), new King(Team.BLACK)
        ));

        assertThat(new ChessGame(board).findOutcome()).isEqualTo(Outcome.WHITE_WIN);
    }

    @DisplayName("체크상태가 아닐 시, 기물의 점수의 합이 검은색 팀이 클 경우 검은색 팀의 승이다.")
    @Test
    void findOutcomeBlackWin() {
        Board board = new Board(Map.of(
                Position.of(File.A, Rank.ONE), new King(Team.WHITE),
                Position.of(File.A, Rank.SEVEN), new King(Team.BLACK),
                Position.of(File.B, Rank.EIGHT), new Pawn(Team.BLACK)
        ));

        assertThat(new ChessGame(board).findOutcome()).isEqualTo(Outcome.BLACK_WIN);
    }
}
