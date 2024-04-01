package chess.domain;

import chess.domain.piece.*;
import chess.domain.piece.character.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Positions;
import chess.domain.position.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessGameTest {
    @DisplayName("기물을 움직일 수 있고, 기물을 움직일 수 있는 팀은 변경된다.")
    @Test
    void movePiece() {
        Board board = new Board(BoardFactory.generateStartBoard());
        ChessGame chessGame = new ChessGame(board, Team.WHITE);

        chessGame.movePiece(new Positions(
                Position.of(File.E, Rank.TWO),
                Position.of(File.E, Rank.FOUR)));

        assertThat(chessGame.currentTeam()).isEqualTo(Team.BLACK);
    }

    @DisplayName("현재 진행 팀이 체크 상태인지 알 수 있다.")
    @Test
    void findCheck() {
        Board board = new Board(Map.of(
                Position.of(File.H, Rank.ONE), new King(Team.WHITE),
                Position.of(File.G, Rank.ONE), new Pawn(Team.WHITE),
                Position.of(File.G, Rank.TWO), new Knight(Team.WHITE),
                Position.of(File.H, Rank.FOUR), new Rook(Team.BLACK),
                Position.of(File.A, Rank.ONE), new King(Team.BLACK)
        ));
        ChessGame chessGame = new ChessGame(board, Team.BLACK);

        assertThat(chessGame.findCheck()).isEqualTo(CheckState.CHECK);
    }

    @DisplayName("이전 진행 팀이 체크 상태였는데, 체크 상태를 벗어나지 않았을 경우 오류가 발생한다.")
    @Test
    void invalidFindCheck() {
        Board board = new Board(Map.of(
                Position.of(File.H, Rank.ONE), new King(Team.WHITE),
                Position.of(File.G, Rank.ONE), new Pawn(Team.WHITE),
                Position.of(File.G, Rank.TWO), new Knight(Team.WHITE),
                Position.of(File.H, Rank.FOUR), new Rook(Team.BLACK)
        ));
        ChessGame chessGame = new ChessGame(board, Team.WHITE);

        assertThatThrownBy(chessGame::findCheck)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("체크 상태를 벗어나지 않았습니다.");
    }

    @DisplayName("체크 메이트 상태일 시, 체크 메이트 한 팀이 승이다.")
    @Test
    void findOutcomeCheckMateWin() {
        Board board = new Board(Map.of(
                Position.of(File.H, Rank.ONE), new King(Team.BLACK),
                Position.of(File.H, Rank.TWO), new Pawn(Team.BLACK),
                Position.of(File.F, Rank.ONE), new Queen(Team.WHITE),
                Position.of(File.A, Rank.ONE), new King(Team.WHITE)
        ));

        assertThat(new ChessGame(board, Team.WHITE).findOutcome())
                .isEqualTo(Outcome.WHITE_WIN);
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

        assertThat(new ChessGame(board, Team.WHITE).findOutcome())
                .isEqualTo(Outcome.DRAW);
    }

    @DisplayName("체크상태가 아닐 시, 기물의 점수의 합이 흰색 팀이 클 경우 흰색 팀의 승이다.")
    @Test
    void findOutcomeWhiteWin() {
        Board board = new Board(Map.of(
                Position.of(File.A, Rank.ONE), new King(Team.WHITE),
                Position.of(File.B, Rank.TWO), new Pawn(Team.WHITE),
                Position.of(File.A, Rank.SEVEN), new King(Team.BLACK)
        ));

        assertThat(new ChessGame(board, Team.WHITE).findOutcome())
                .isEqualTo(Outcome.WHITE_WIN);
    }

    @DisplayName("체크상태가 아닐 시, 기물의 점수의 합이 검은색 팀이 클 경우 검은색 팀의 승이다.")
    @Test
    void findOutcomeBlackWin() {
        Board board = new Board(Map.of(
                Position.of(File.A, Rank.ONE), new King(Team.WHITE),
                Position.of(File.A, Rank.SEVEN), new King(Team.BLACK),
                Position.of(File.B, Rank.EIGHT), new Pawn(Team.BLACK)
        ));

        assertThat(new ChessGame(board, Team.WHITE).findOutcome())
                .isEqualTo(Outcome.BLACK_WIN);
    }

    @DisplayName("팀과 각 팀에 대한 점수가 매핑된 정보을 알 수 있다.")
    @Test
    void mapTeamAndScore() {
        Board board = new Board(Map.of(
                Position.of(File.H, Rank.ONE), new King(Team.WHITE),
                Position.of(File.G, Rank.ONE), new Pawn(Team.WHITE),
                Position.of(File.G, Rank.TWO), new Knight(Team.WHITE),
                Position.of(File.H, Rank.FOUR), new Rook(Team.BLACK)
        ));
        ChessGame chessGame = new ChessGame(board, Team.WHITE);

        assertThat(chessGame.mapTeamAndScore())
                .isEqualTo(Map.of(
                        Team.WHITE, 3.5,
                        Team.BLACK, 5.0
                ));
    }
}
